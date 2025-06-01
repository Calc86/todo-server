package ru.xsrv.todo.ru.xsrv.todo.db.tables.user

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.VAR_CHAR_MAX_LENGTH

object UsersAuth : IntIdTable(Names.TABLE) {

    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE)

    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime).index()
    val type = enumeration(Names.TYPE, Types::class).index()
    val auth_id = varchar(Names.AUTH_ID, length = VAR_CHAR_MAX_LENGTH)
    val auth_data = text(Names.AUTH_DATA)

    init {
        uniqueIndex(type, auth_id)
    }

    enum class Types {
        O_AUTH,
        LDAP
    }

    private object Names {
        const val TABLE = "users_auth"

        const val USER_ID = "user_id"
        const val TYPE = "type"
        const val DATE = "date"
        const val AUTH_ID = "auth_id"
        const val AUTH_DATA = "auth_data"
    }
}
