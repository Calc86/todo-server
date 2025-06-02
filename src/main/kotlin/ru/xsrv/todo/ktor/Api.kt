package ru.xsrv.todo.ru.xsrv.todo.ktor

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.userLogin
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.userProfile
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.userRegister
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.ValidationException
import ru.xsrv.todo.ru.xsrv.todo.services.AuthService
import ru.xsrv.todo.ru.xsrv.todo.services.UserService

fun Application.configureApi() {
    val userService by inject<UserService>()
    val authService by inject<AuthService>()

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
            get("/settings") {
                call.respond(mapOf("proto" to 1))
            }

            route("/user") {
                userLogin("/login", userService, authService)
                userRegister("/register", userService)
//                authenticate(optional = true) {// optional throw token expired, and work without token
                authenticate {
                    userProfile("/profile")
                }
            }

            route("/auth") {
                authenticate {
                    get("/refresh-token") { // получить токен для обновления токена авторизации
                        val principal = call.principal<UserPrincipal>()!!
                        val token = authService.generateRefreshToken(authService.select(principal.session)!!)
                        call.respond(token)
                    }

                    // обновить токен авторизации
                    get("/refresh") {
                        val principal = call.principal<UserPrincipal>()!!
                        val token = authService.generateAuthToken(authService.select(principal.session)!!)
                        call.respond(token)
                    }

                    // проверить токен авторизации
                    get("/check") {
                        call.respond(HttpStatusCode.OK)
                    }

                    get("/logout") {
                        val principal = call.principal<UserPrincipal>()!!
                        authService.logout(principal.session)
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
        }
    }
}
