package ru.xsrv.todo.ru.xsrv.todo.ktor

data class JWTConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val ttl: Int,
    val refreshTtl: Int,
)
