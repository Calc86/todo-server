package ru.xsrv.todo.ru.xsrv.todo.ktor.action

import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import ru.xsrv.todo.ru.xsrv.todo.ktor.validate
import ru.xsrv.todo.ru.xsrv.todo.models.requests.Auth
import ru.xsrv.todo.ru.xsrv.todo.services.AuthService
import ru.xsrv.todo.ru.xsrv.todo.services.UserService

@KtorDsl
fun Route.userLogin(
    path: String,
    userService: UserService,
    authService: AuthService,
): Route = post(path) {
    val auth = call.receive<Auth>()
    auth.validate(this, Auth.validator) {
        // Check username and password
        val user = userService.selectUser(auth.email!!, auth.password!!) ?: run {
            call.respond(UnauthorizedResponse())
            return@validate
        }

        // create session
        val session = authService.createSession(user.id, auth.deviceType, auth.deviceId)

        call.respond(authService.generateAuthToken(session))
    }
}
