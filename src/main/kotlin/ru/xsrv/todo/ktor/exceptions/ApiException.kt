package ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions

open class ApiException(
    message: String,
    val code: Int,
) : RuntimeException(message)
