package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.Mapper
import ru.xsrv.todo.ru.xsrv.todo.db.entities.TodoEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.Todos
import ru.xsrv.todo.ru.xsrv.todo.db.validateNotBlankOrNull
import ru.xsrv.todo.ru.xsrv.todo.models.requests.Validator

@Serializable
data class Todo(
    val title: String,
    val id: Int = 0,
    val description: String = "",
    val status: Todos.Status = Todos.Status.NEW,
    // todo 20250602 add other fields
) {
    companion object {
        val entityMapper: Mapper<TodoEntity, Todo> = { todo ->
            Todo(
                todo.title,
                todo.id.value,
                todo.description,
                todo.status,
            )
        }

        val validator: Validator<Todo> = { todo ->
            todo.title.validateNotBlankOrNull("name")
            // todo.status.validateEnum(Todos.Status.entries, "status") // todo 20250607
        }
    }
}
