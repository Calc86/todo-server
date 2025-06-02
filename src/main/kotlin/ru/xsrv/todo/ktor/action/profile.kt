package ru.xsrv.todo.ru.xsrv.todo.ktor.action

import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import ru.xsrv.todo.ru.xsrv.todo.ktor.UserPrincipal

@KtorDsl
fun Route.userProfile(
    path: String,
): Route = get(path) {
    val principal = call.principal<UserPrincipal>()!!
    call.respond(principal.user)
}
