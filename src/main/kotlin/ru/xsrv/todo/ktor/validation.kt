package ru.xsrv.todo.ru.xsrv.todo.ktor

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


suspend fun RoutingContext.validate(validate: suspend () -> Unit, block: suspend () -> Unit) {
    try {
        validate()
        block()
    } catch (exception: ValidationException) {
        call.respond(HttpStatusCode.BadRequest, exception.message ?: "ValidationException")
    }
}

suspend fun <T> T.validate(context: RoutingContext, validate: suspend (T) -> Unit, block: suspend () -> Unit) {
    try {
        validate(this)
        block()
    } catch (exception: ValidationException) {
        context.call.respond(HttpStatusCode.BadRequest, exception.message ?: "ValidationException")
    }
}
