package ru.xsrv.todo.ru.xsrv.todo.db.tables.todo

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users

object Todos : IntIdTable(Names.TABLE) {

    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE)
    val type = enumeration(Names.TYPE, Types::class)
    val repeating = enumeration(Names.REPEATING, Repeating::class)
    val date1 = datetime(Names.DATE1).nullable().default(null)
    val date2 = datetime(Names.DATE2).nullable().default(null)
    val dateTZ = integer(Names.DATE_TZ).default(0)
    val status = enumeration(Names.STATUS, Status::class).index()
    val dateNext = datetime(Names.DATE_NEXT).nullable().default(null).index()

    enum class Types {
        /** в любое время */
        ANYTIME,

        /** конкретное время */
        ONETIME,

        /** конкретная дата */
        DATE,

        /** от-до */
        TIME_INTERVAL,

        /** количество часов */
        PERIOD
    }

    enum class Repeating { NO, YES }

    enum class Status {
        DONE,
        NEW,
        REPEATED,
        DELAY
    }

    private object Names {
        const val TABLE = "todos"

        const val USER_ID = "user_id"
        const val TYPE = "type"
        const val REPEATING = "repeating"
        const val DATE1 = "date1"
        const val DATE2 = "date2"
        const val DATE_TZ = "date_tz"
        const val STATUS = "status"
        const val DATE_NEXT = "date_next"
    }
}
