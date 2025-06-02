package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.Mapper
import ru.xsrv.todo.ru.xsrv.todo.db.entities.TodoEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.Todos

@Serializable
data class Todo(
    val id: Int,
    val title: String,
    val description: String,
    val status: Todos.Status,
    // todo 20250602 add other fields
) {
    companion object {
        val entityMapper: Mapper<TodoEntity, Todo> = { todo ->
            Todo(
                todo.id.value,
                todo.title,
                todo.description,
                todo.status,
            )
        }
    }
}
