package ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions

import io.ktor.http.*

class HttpException(
    val statusCode: HttpStatusCode,
    message: String,
    code: Int = 0,
) : ApiException(message, code)
