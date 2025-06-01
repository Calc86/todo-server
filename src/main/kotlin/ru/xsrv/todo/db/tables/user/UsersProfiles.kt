package ru.xsrv.todo.ru.xsrv.todo.db.tables.user

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.xsrv.todo.ru.xsrv.todo.db.tables.VAR_CHAR_MAX_LENGTH

object UsersProfiles : IntIdTable(Names.TABLE) {

    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE)
    val name = varchar(Names.NAME, length = VAR_CHAR_MAX_LENGTH).uniqueIndex()

    private object Names {
        const val TABLE = "users_profiles"

        const val USER_ID = "user_id"
        const val NAME = "name"
    }
}
