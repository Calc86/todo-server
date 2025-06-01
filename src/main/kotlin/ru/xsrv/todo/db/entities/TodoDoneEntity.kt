package ru.xsrv.todo.ru.xsrv.todo.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.TodoDone

class TodoDoneEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TodoDoneEntity>(TodoDone)

    var todo by TodoDone.todo
    var repeating by TodoDone.repeating
    var date by TodoDone.date
    var comment by TodoDone.comment
}
