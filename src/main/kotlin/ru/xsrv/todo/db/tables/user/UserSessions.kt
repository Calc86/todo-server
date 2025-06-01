package ru.xsrv.todo.ru.xsrv.todo.db.tables.user

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.VAR_CHAR_MAX_LENGTH

object UserSessions : IntIdTable(Names.TABLE) {

    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE).nullable().default(null)  // null - anonymous

    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime).index()
    val closed = integer(Names.CLOSED).default(0).index()
    val device = enumeration(Names.DEVICE, Users.Device::class).default(Users.Device.OTHER).index()
    val device_id = uuid(Names.DEVICE_ID).nullable().default(null)
    val pushType = enumeration(Names.PUSH_TYPE, PushTypes::class).default(PushTypes.UNKNOWN)
    val push = varchar(Names.PUSH, VAR_CHAR_MAX_LENGTH).nullable().default(null)

    enum class PushTypes {
        UNKNOWN,
        FIREBASE,
    }

    private object Names {
        const val TABLE = "user_sessions"

        const val USER_ID = "user_id"
        const val DATE = "date"
        const val CLOSED = "closed"
        const val DEVICE = "device"
        const val DEVICE_ID = "device_id"
        const val PUSH_TYPE = "push_type"
        const val PUSH = "push"
    }
}
