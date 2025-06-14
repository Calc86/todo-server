package ru.xsrv.todo.ru.xsrv.todo.ktor

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.Todos
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.userLogin
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.userProfile
import ru.xsrv.todo.ru.xsrv.todo.ktor.action.userRegister
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.HttpException
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.ValidationException
import ru.xsrv.todo.ru.xsrv.todo.models.ShopList
import ru.xsrv.todo.ru.xsrv.todo.models.Todo
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

            route("/todo") {
                authenticate {
                    post("/create") {
                        val principal = call.principal<UserPrincipal>()!!
                        val todo = call.receive<Todo>()
                        todo.validate(this, Todo.validator) {
                            val result = todoService.create(principal.user.id, todo)
                            call.respond(result)
                        }
                    }
                    post("/update") {
                        val principal = call.principal<UserPrincipal>()!!
                        val todo = call.receive<Todo>()
                        todo.validate(this, Todo.validator) {
                            val result = todoService.update(principal.user.id, todo)
                            call.respond(result)
                        }
                    }
                    post("/done") {
                        val principal = call.principal<UserPrincipal>()!!
                        val todo = call.receive<Todo>() // todo 20250602 may be int id
                        todo.copy(status = Todos.Status.DONE).validate(this, Todo.validator) {
                            val result = todoService.update(principal.user.id, todo)
                            call.respond(result)
                        }
                    }
                    post("/delete") {
                        val principal = call.principal<UserPrincipal>()!!
                        val todo = call.receive<Todo>() // todo 20250602 may be int id
                        todoService.selectEntity(principal.user.id, todo)
                            ?.delete()
                            ?: throw HttpException(HttpStatusCode.NotFound, "Todo with id ${todo.id} not found for current user")
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
            route("shop-list") {
                authenticate {
                    post("/create") {
                        val principal = call.principal<UserPrincipal>()!!
                        val list = call.receive<ShopList>()
                        list.validate(this, ShopList.validator) {
                            val result = listService.create(principal.user.id, list)
                            call.respond(result)
                        }
                    }
                    post("/update") {
                        val principal = call.principal<UserPrincipal>()!!
                        val list = call.receive<ShopList>()
                        list.validate(this, ShopList.validator) {
                            val result = listService.update(principal.user.id, list)
                            call.respond(result)
                        }
                    }
                    post("/done") {
                        val principal = call.principal<UserPrincipal>()!!
                        val list = call.receive<ShopList>() // todo 20250602 may be int id
                        list.copy(status = ShopLists.Status.DONE).validate(this, ShopList.validator) {
                            val result = listService.update(principal.user.id, list)
                            call.respond(result)
                        }
                    }
                    post("/delete") {
                        val principal = call.principal<UserPrincipal>()!!
                        val list = call.receive<ShopList>() // todo 20250602 may be int id
                        listService.selectEntity(principal.user.id, list)
                            ?.delete()
                            ?: throw HttpException(HttpStatusCode.NotFound, "ShopList with id ${list.id} not found for current user")
                        call.respond(HttpStatusCode.OK)
                    }
                }

            }
        }
    }
}
