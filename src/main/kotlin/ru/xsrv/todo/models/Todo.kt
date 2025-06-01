package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.Todos

@Serializable
data class Todo (
    val id: Int,
    val title: String,
    val description: String,
    val status: Todos.Status,
)   // todo 20250602 add another fields
