package ru.xsrv.todo.ru.xsrv.todo.ktor

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.userLogin
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.userRegister
import ru.xsrv.todo.ru.xsrv.todo.services.UserService

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
                userLogin("/login", userService, jwt)
                userRegister("/register", userService)

//                authenticate(optional = true) {// optional throw token expired, and work without token
                authenticate {
                    get("/profile") {
                        // todo 20250602 check auth
                        val principal = call.principal<UserPrincipal>()!!
                        //val username = principal!!.payload.getClaim("username").asString()
                        call.respond(principal.user)
                    }
                }
            }
        }
    }
}
