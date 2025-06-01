package ru.xsrv.todo.ru.xsrv.todo

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.xsrv.todo.ru.xsrv.todo.db.UnknownInsertErrorException
import ru.xsrv.todo.ru.xsrv.todo.db.checkPassword
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserEntity
import ru.xsrv.todo.ru.xsrv.todo.db.hashPassword
import ru.xsrv.todo.ru.xsrv.todo.db.returnOnTrueOrNull
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.UsersProfiles
import ru.xsrv.todo.ru.xsrv.todo.models.User
import kotlin.coroutines.CoroutineContext

class UserService(
    database: Database,
    private val context: CoroutineContext = Dispatchers.IO,
) {
    init {
        transaction(database) {
            SchemaUtils.create(Users)
            SchemaUtils.create(UsersProfiles)
        }
    }

    suspend fun registerByEmail(email: String, name: String, password: String): UserEntity = dbQuery {
        Users.insertAndGetId {
            it[this.email] = email
            it[this.password] = password.hashPassword()
        }.value.also { id ->
            UsersProfiles.insert {
                it[user] = id
                it[this.name] = name
            }
        }.let(UserEntity::findById) ?: throw UnknownInsertErrorException("registerByEmail")
    }

    private fun selectUserBlocking(id: Int): User? = UserEntity.findById(id)?.let(User.entityMapper)

    suspend fun selectUser(id: Int): User? = dbQuery { selectUserBlocking(id) }

    suspend fun selectUser(email: String, password: String): User? = dbQuery {
        UserEntity.find {
            Users.email eq email
            Users.password.isNotNull()
        }
            .firstOrNull()
            ?.let { password.checkPassword(it.password).returnOnTrueOrNull(it) }
            ?.let(User.entityMapper)
    }

//    suspend fun create(user: ExposedUser): Int = dbQuery {
//        Users.insert {
//            it[name] = user.name
//            it[age] = user.age
//        }[Users.id].value
//    }

//    suspend fun update(id: Int, user: ExposedUser) {
//        dbQuery {
//            Users.update({ Users.id eq id }) {
//                it[name] = user.name
//                it[age] = user.age
//            }
//        }
//    }

    suspend fun delete(id: Int) {
        dbQuery {
            Users.deleteWhere { Users.id.eq(id) }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(context) { block() }
}

