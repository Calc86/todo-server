package ru.xsrv.todo.ru.xsrv.todo.ktor

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.xsrv.todo.ru.xsrv.todo.UserService
import ru.xsrv.todo.ru.xsrv.todo.models.requests.Auth
import ru.xsrv.todo.ru.xsrv.todo.models.requests.Register
import java.util.*

fun Application.configureApi() {
    val userService by inject<UserService>()
    val jwt by inject<JWTConfig>()

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

                        // todo 20250601 create session

                        val token = JWT.create()
                            .withAudience(jwt.audience)
                            .withIssuer(jwt.issuer)
                            .withClaim("email", user.email)
                            .withClaim("sid", "any")
                            .withClaim("id", user.id)
                            .withExpiresAt(Date(System.currentTimeMillis() + jwt.ttl * 1000))
                            .sign(Algorithm.HMAC256(jwt.secret))
                        call.respond(
                            hashMapOf(
                                "token" to token,
                                "ttl" to jwt.ttl,
                            )
                        )
                    }
                }
                post("/register") {
                    val register = call.receive<Register>()
                    register.validate(this, Register.validator) {
                        // check in database
                        val user = userService.selectUser(register.email!!)
                        if (user != null) {
                            call.respond(HttpStatusCode.Conflict, "User already registered")
                        } else {
                            val a = userService.registerUser(register)
                            call.respond(a)
                        }
                    }
                }
                get("/profile") {
                    // todo 20250602 check auth

                }
            }
        }
    }
}
