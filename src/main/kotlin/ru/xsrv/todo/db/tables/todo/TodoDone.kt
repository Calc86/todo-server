package ru.xsrv.todo.ru.xsrv.todo.db.tables.todo

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object TodoDone : IntIdTable(Names.TABLE) {

    val todo = reference(Names.TODO_ID, Todos.id, onDelete = ReferenceOption.CASCADE)
    val repeating = reference(Names.REPEATING_ID, TodoRepeating.id, onDelete = ReferenceOption.CASCADE)
    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime).index()
    val comment = text(Names.COMMENT).default("")

    private object Names {
        const val TABLE = "todo_done"

        const val TODO_ID = "todo_id"
        const val REPEATING_ID = "repeating_id"
        const val DATE = "date"
        const val COMMENT = "comment"
    }
}
