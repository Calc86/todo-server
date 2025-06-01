package ru.xsrv.todo.ru.xsrv.todo.ktor

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import ru.xsrv.todo.ru.xsrv.todo.UserService
import ru.xsrv.todo.ru.xsrv.todo.models.requests.Auth
import java.sql.Connection.TRANSACTION_SERIALIZABLE
import java.util.*

fun Application.configureApi() {
    val dbConfig = environment.dbConfig()
    val database = Database.connect(
        url = dbConfig.url,
        driver = dbConfig.driver,
        user = dbConfig.user,
        password = dbConfig.password,
    )
    if(dbConfig.isSQLite) TransactionManager.manager.defaultIsolationLevel = TRANSACTION_SERIALIZABLE

    val userService = UserService(database)
    routing {
        route("/api") {
            get {
                call.respondText("Api blank page")
            }
            get("/check/user") {
                call.respondText("check user page")
            }
            route("/user") {
                post("/login") {
                    val auth = call.receive<Auth>()
                    auth.validate(this, Auth.validator) {
                        // Check username and password
                        val user = userService.selectUser(auth.email!!, auth.password!!) ?: run {
                            call.respond(UnauthorizedResponse())
                            return@validate
                        }

                        // ...
                        val jwt = environment.jwtConfig()

                        val token = JWT.create()
                            .withAudience(jwt.audience)
                            .withIssuer(jwt.issuer)
                            .withClaim("email", auth.email)
                            .withExpiresAt(Date(System.currentTimeMillis() + jwt.ttl * 1000))
                            .sign(Algorithm.HMAC256(jwt.secret))
                        call.respond(hashMapOf(
                            "email" to auth.email,
                            "token" to token,
                        ))
                    }
                }
            }
        }
    }
}

data class JWTConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val ttl: Int
)

fun ApplicationEnvironment.jwtConfig() = let { environment ->
    JWTConfig(
        secret = environment.config.property("jwt.secret").getString(),
        issuer = environment.config.property("jwt.domain").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        realm = environment.config.property("jwt.realm").getString(),
        ttl = environment.config.property("jwt.ttl").getString().toInt(),
    )
}

data class DBConfig(
    val url: String,
    val user: String,
    val driver: String,
    val password: String,
) {
    val isSQLite = driver.contains("sqlite", true)
}

fun ApplicationEnvironment.dbConfig() = let { environment ->
    DBConfig(
        url = environment.config.property("db.url").getString(),
        driver = environment.config.property("db.driver").getString(),
        user = environment.config.property("db.user").getString(),
        password = environment.config.property("db.password").getString(),
    )
}

class ValidationException(message: String) : RuntimeException(message)

suspend fun RoutingContext.validate(validate: suspend () -> Unit, block: suspend () -> Unit) {
    try {
        validate()
        block()
    } catch (exception: ValidationException) {
        call.respond(HttpStatusCode.BadRequest, exception.message ?: "ValidationException")
    }
}

suspend fun <T> T.validate(context: RoutingContext, validate: suspend (T) -> Unit, block: suspend () -> Unit) {
    try {
        validate(this)
        block()
    } catch (exception: ValidationException) {
        context.call.respond(HttpStatusCode.BadRequest, exception.message ?: "ValidationException")
    }
}
