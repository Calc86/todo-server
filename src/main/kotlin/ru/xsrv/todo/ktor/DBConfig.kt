package ru.xsrv.todo.ru.xsrv.todo.ktor

data class DBConfig(
    val url: String,
    val user: String,
    val driver: String,
    val password: String,
) {
    val isSQLite = driver.contains("sqlite", true)
}