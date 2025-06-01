package ru.xsrv.todo.ru.xsrv.todo.db.tables.user

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import ru.xsrv.todo.ru.xsrv.todo.db.VAR_CHAR_MAX_LENGTH

object UsersProfiles : IdTable<Int>(Names.TABLE) {

    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE)
    val name = varchar(Names.NAME, length = VAR_CHAR_MAX_LENGTH).uniqueIndex()

    private object Names {
        const val TABLE = "users_profiles"

        const val USER_ID = "user_id"
        const val NAME = "name"
    }

    override val primaryKey: PrimaryKey = PrimaryKey(user)

    override val id: Column<EntityID<Int>>
        get() = user
}
