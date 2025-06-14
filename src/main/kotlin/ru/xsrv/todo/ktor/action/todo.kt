package ru.xsrv.todo.ru.xsrv.todo.ktor.action

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.Todos
import ru.xsrv.todo.ru.xsrv.todo.ktor.UserPrincipal
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.HttpException
import ru.xsrv.todo.ru.xsrv.todo.ktor.validate
import ru.xsrv.todo.ru.xsrv.todo.models.Todo
import ru.xsrv.todo.ru.xsrv.todo.services.TodoService

fun Route.todoCreate(
    path: String,
    todoService: TodoService,
): Route = post(path) {

    val principal = call.principal<UserPrincipal>()!!
    val todo = call.receive<Todo>()
    todo.validate(this, Todo.validator) {
        val result = todoService.create(principal.user.id, todo)
        call.respond(result)
    }
}


fun Route.todoUpdate(
    path: String,
    todoService: TodoService,
): Route = post(path) {

    val principal = call.principal<UserPrincipal>()!!
    val todo = call.receive<Todo>()
    todo.validate(this, Todo.validator) {
        val result = todoService.update(principal.user.id, todo)
        call.respond(result)
    }
}


fun Route.todoDone(
    path: String,
    todoService: TodoService,
): Route = post(path) {

    val principal = call.principal<UserPrincipal>()!!
    val todo = call.receive<Todo>() // todo 20250602 may be int id
    todo.copy(status = Todos.Status.DONE).validate(this, Todo.validator) {
        val result = todoService.update(principal.user.id, todo)
        call.respond(result)
    }
}


fun Route.todoDelete(
    path: String,
    todoService: TodoService,
): Route = post(path) {

    val principal = call.principal<UserPrincipal>()!!
    val todo = call.receive<Todo>() // todo 20250602 may be int id
    todoService.selectEntity(principal.user.id, todo)
        ?.delete()
        ?: throw HttpException(HttpStatusCode.NotFound, "Todo with id ${todo.id} not found for current user")
    call.respond(HttpStatusCode.OK)
}
