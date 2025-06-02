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
        get("/throw-validation") {
            throw ValidationException("throw-validation")   // 400 html
        }
        route("/api") {
            get("/throw-validation") {
                throw ValidationException("throw-validation")   // 400 json
            }
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
                        val principal = call.principal<UserPrincipal>()!!
                        call.respond(principal.user)
                    }
                }
            }
        }
    }
}
