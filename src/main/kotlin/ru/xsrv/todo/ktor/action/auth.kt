package ru.xsrv.todo.ru.xsrv.todo.ktor.action

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.xsrv.todo.ru.xsrv.todo.ktor.UserPrincipal
import ru.xsrv.todo.ru.xsrv.todo.services.AuthService

fun Route.authRefreshToken(
    path: String,
    authService: AuthService,
): Route = get(path) {
    val principal = call.principal<UserPrincipal>()!!
    val token = authService.generateRefreshToken(authService.select(principal.session)!!)
    call.respond(token)
}

fun Route.authRefresh(
    path: String,
    authService: AuthService,
): Route = get(path) {
    val principal = call.principal<UserPrincipal>()!!
    val token = authService.generateAuthToken(authService.select(principal.session)!!)
    call.respond(token)
}


fun Route.authCheck(
    path: String,
    authService: AuthService,
): Route = get(path) {
    call.respond(HttpStatusCode.OK)
}


fun Route.authLogout(
    path: String,
    authService: AuthService,
): Route = get(path) {
    val principal = call.principal<UserPrincipal>()!!
    authService.logout(principal.session)
    call.respond(HttpStatusCode.OK)
}

