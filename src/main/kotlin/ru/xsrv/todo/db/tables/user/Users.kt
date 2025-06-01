package ru.xsrv.todo.ru.xsrv.todo.db.tables.user

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.tables.VAR_CHAR_MAX_LENGTH

object Users : IntIdTable(Names.TABLE) {

    val uuid = uuid(Names.UUID)
    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime).index()

    //varchar("name", 50).check { it regexp "^[A-Z].*" }
    val email = varchar(Names.EMAIL, length = VAR_CHAR_MAX_LENGTH).uniqueIndex()
    val password = varchar(Names.PASSWORD, length = VAR_CHAR_MAX_LENGTH).nullable().default(null)
    val status = integer(Names.STATUS).default(Status.ENABLED).index()

    object Status {
        const val ENABLED = 10
    }

    private object Names {
        const val TABLE = "users"

        const val UUID = "uuid"
        const val DATE = "date"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val STATUS = "status"
    }
}

