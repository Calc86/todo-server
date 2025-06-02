package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val token: String,
    val ttl: Int,
    val created: Int
)
