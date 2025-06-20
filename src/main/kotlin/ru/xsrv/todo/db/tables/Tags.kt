package ru.xsrv.todo.ru.xsrv.todo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import ru.xsrv.todo.ru.xsrv.todo.db.VAR_CHAR_MAX_LENGTH

object Tags : IntIdTable(Names.TABLE) {

    val name = varchar(Names.NAME, length = VAR_CHAR_MAX_LENGTH)
    val description = text(Names.DESCRIPTION)

    object Names {
        const val TABLE = "tags"

        const val NAME = "name"
        const val DESCRIPTION = "description"
    }
}
