package ru.xsrv.todo.ru.xsrv.todo.ktor.action

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.xsrv.todo.ru.xsrv.todo.ktor.validate
import ru.xsrv.todo.ru.xsrv.todo.models.requests.Register
import ru.xsrv.todo.ru.xsrv.todo.services.UserService

fun Route.userRegister(
    path: String,
    userService: UserService,
): Route = post(path) {
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