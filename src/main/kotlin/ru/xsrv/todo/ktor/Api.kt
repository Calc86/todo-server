package ru.xsrv.todo.ru.xsrv.todo.ktor

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.*
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.user.userLogin
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.user.userProfile
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.user.userRegister
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.ValidationException
import ru.xsrv.todo.ru.xsrv.todo.services.AuthService
import ru.xsrv.todo.ru.xsrv.todo.services.ShopListService
import ru.xsrv.todo.ru.xsrv.todo.services.TodoService
import ru.xsrv.todo.ru.xsrv.todo.services.UserService

fun Application.configureApi() {
    val userService by inject<UserService>()
    val authService by inject<AuthService>()
    val todoService: TodoService by inject<TodoService>()
    val listService: ShopListService by inject<ShopListService>()

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
                    authRefreshToken("/refresh-token", authService)
                    // обновить токен авторизации
                    authRefresh("/refresh", authService)
                    // проверить токен авторизации
                    authCheck("/check", authService)
                    authLogout("/logout", authService)
                }
            }

            route("/todo") {
                authenticate {
                    todoCreate("/create", todoService)
                    todoUpdate("/update", todoService)
                    todoDone("/done", todoService)
                    todoDelete("/delete", todoService)
                }
            }

            route("shop-list") {
                authenticate {
                    listCreate("/create", listService)
                    listUpdate("/update", listService)
                    listDone("/done", listService)
                    listDelete("/delete", listService)
                }
            }
        }
    }
}
