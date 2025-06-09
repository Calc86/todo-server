package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val status: Int,
    val message: String = "ApiError",
    val code: Int = 0,
    val trace: List<String > = listOf()
)
