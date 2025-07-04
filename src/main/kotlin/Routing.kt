package ru.xsrv.todo

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.ApiException
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.HttpException
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.NotImplementedException
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.ValidationException
import ru.xsrv.todo.ru.xsrv.todo.models.ApiError

fun Application.configureRouting() {
    val isDevMode = environment.config.propertyOrNull("ktor.development")?.getString().toBoolean()

    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            if(call.request.path().startsWith("/api")) {    // todo 20250607 check for not /api exception
                val trace = when (isDevMode) {
                    true -> cause.stackTraceToString().split("\n").map { it.trim() }.take(30)
                    false -> listOf()
                }
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiError(
                        status = HttpStatusCode.BadRequest.value,
                        message = cause.message ?: "",
                        code = 0,
                        trace = trace,
                    )
                )
            }

        }
        exception<ApiException> { call, cause ->
            when (cause) {
                is HttpException -> call.respond(
                    status = cause.statusCode,
                    message = ApiError(cause.statusCode.value, message = cause.message ?: "", cause.code)
                )

                else -> call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = ApiError(HttpStatusCode.InternalServerError.value, cause.message ?: "ApiException")
                )
            }
        }
        exception<ValidationException> { call, cause ->
            if (call.request.path().startsWith("/api")) {
                val trace = when (isDevMode) {
                    true -> cause.stackTraceToString().split("\n").map { it.trim() }.take(30)
                    false -> listOf()
                }
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiError(
                        HttpStatusCode.BadRequest.value,
                        cause.message ?: cause.javaClass.simpleName,
                        0,
                        trace
                    )
                )
            } else call.respondText(
                text = "400: $cause",
                status = HttpStatusCode.BadRequest
            )
        }

        exception<NotImplementedException> { call, cause ->
            call.respondText(
                text = "501: $cause",
                status = HttpStatusCode.NotImplemented
            )
        }

        exception<Throwable> { call, cause ->
            val trace = when (isDevMode) {
                true -> cause.stackTraceToString().split("\n").map { it.trim() }.take(30)
                false -> listOf()
            }
            if(call.request.path().startsWith("/api")) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiError(
                        status = HttpStatusCode.InternalServerError.value,
                        message = HttpStatusCode.InternalServerError.description,
                        trace = trace
                    )
                )
            } else {
                call.respondText(
                    text = "500: $cause \n${trace.joinToString(separator = "\n")}",
                    status = HttpStatusCode.InternalServerError
                )
            }
        }
        status(HttpStatusCode.NotFound) { call, status ->
            if(call.request.path().startsWith("/api")) {
                call.respond(
                    status,
                    ApiError(
                        status = status.value,
                        message = status.description,
                    )
                )
            } else {
                call.respondText(text = "${status.value}: ${status.description}", status = status)   // todo 20250602 add some template
            }
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}
