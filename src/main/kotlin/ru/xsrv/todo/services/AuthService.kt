package ru.xsrv.todo.ru.xsrv.todo.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.xsrv.todo.ru.xsrv.todo.db.DBException
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserEntity
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserSessionEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.UserSessions
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users
import ru.xsrv.todo.ru.xsrv.todo.ktor.JWTConfig
import ru.xsrv.todo.ru.xsrv.todo.ktor.UserPrincipal
import ru.xsrv.todo.ru.xsrv.todo.models.Token
import java.util.*
import kotlin.coroutines.CoroutineContext

class AuthService(
    val jwt: JWTConfig,
    database: Database,
    private val context: CoroutineContext = Dispatchers.IO,
) {
    init {
        transaction(database) {
            SchemaUtils.create(UserSessions)
        }
    }

    suspend fun createSession(
        userId: Int,
        device: Users.Device,
        deviceId: UUID,
    ): UserSessionEntity = dbQuery {
        val user = UserEntity.findById(userId) ?: throw DBException("selectUser")
        UserSessions.update ({ (UserSessions.device eq device) and (UserSessions.device_id eq deviceId) }) {
            it[closed] = 1
        }
        UserSessionEntity.new {
            this.userId = user.id
            this.device = device
            this.deviceId = deviceId
        }
        // todo 20250602 close other sessions with same device/deviceId
    }

    suspend fun select(id: Int ): UserSessionEntity? = dbQuery { UserSessionEntity.findById(id) }

    private suspend fun generateToken( // todo 20250602 make as Session extension
        session: UserSessionEntity,
        ttl: Int,
        type: UserPrincipal.Types
    ): Token = dbQuery {
        val created = System.currentTimeMillis()
        val token = JWT.create()
            .withAudience(jwt.audience)
            .withIssuer(jwt.issuer)
            .withClaim(UserPrincipal.CLAIM_EMAIL, UserEntity.findById(session.userId!!)!!.email)    // todo 20250602 nullable
            .withClaim(UserPrincipal.CLAIM_SID, session.id.value)
            .withClaim(UserPrincipal.CLAIM_ID, session.userId?.value)
            .withClaim(UserPrincipal.CLAIM_TYPE, type.toString())
            .withExpiresAt(Date(created + ttl * 1000L))
            .sign(Algorithm.HMAC256(jwt.secret))
        Token(
            token,
            ttl,
            (created / 1000).toInt()
        )
    }

    suspend fun generateAuthToken(session: UserSessionEntity) =
        generateToken(session, jwt.ttl, UserPrincipal.Types.AUTH)

    suspend fun generateRefreshToken(session: UserSessionEntity) =
        generateToken(session, jwt.refreshTtl, UserPrincipal.Types.REFRESH)

    suspend fun logout(sessionId: Int) = dbQuery {
        UserSessionEntity.findByIdAndUpdate(sessionId) {s ->
            s.closed = 1
        } ?: throw DBException("logout")
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(context) { block() }
}
