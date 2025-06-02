package ru.xsrv.todo.ru.xsrv.todo.db.tables.todo

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users

/**
 * Попросить "помощь зала". Пусть друг это сделает
 */
object TodoFriends : Table(Names.TABLE) {

    val todo = reference(Names.TODO_ID, Todos.id, onDelete = ReferenceOption.CASCADE)
    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE)
    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime)
    val comment = text(Names.COMMENT).default("")

    override val primaryKey = PrimaryKey(todo, user)

    private object Names {
        const val TABLE = "todo_friends"

        const val TODO_ID = "todo_id"
        const val USER_ID = "user_id"
        const val DATE = "date"
        const val COMMENT = "comment"
    }
}

