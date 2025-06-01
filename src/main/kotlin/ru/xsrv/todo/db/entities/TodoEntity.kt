package ru.xsrv.todo.ru.xsrv.todo.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.Todos

class TodoEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TodoEntity>(Todos)

    var user by Todos.user
    var type by Todos.type
    var repeating by Todos.repeating
    var date1 by Todos.date1
    var date2 by Todos.date2
    var dateTZ by Todos.dateTZ
    var title by Todos.title
    var description by Todos.description
    var status by Todos.status
    var dateNext by Todos.dateNext

    // todo 20250602 add relations
}
