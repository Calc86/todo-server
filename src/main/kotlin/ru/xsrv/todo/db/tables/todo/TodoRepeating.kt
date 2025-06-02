package ru.xsrv.todo.ru.xsrv.todo.db.tables.todo

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Если нужно что то повторить, то указываем время когад это нужно повторять и как часто
 */
object TodoRepeating : IntIdTable(Names.TABLE) {

    val todo = reference(Names.TODO_ID, Todos.id, onDelete = ReferenceOption.CASCADE)
    val repeating_unit = enumeration(Names.REPEATING_UNIT, RepeatingUnit::class)
    val repeating_value = integer(Names.REPEATING_VALUE).default(0)
    val dateTZ = integer(Names.DATE_TZ).default(0)

    enum class RepeatingUnit {
        /** каждый n часов */
        N_HOUR,

        /** каждый n дней */
        N_DAY,

        /** каждый n месяцев */
        N_MONTH,

        /** дни недели */
        WEEKDAYS,

        /** дни месяца */
        DAY_MONTH
    }

    private object Names {
        const val TABLE = "todo_repeating"

        const val TODO_ID = "todo_id"
        const val REPEATING_UNIT = "repeating_unit"
        const val REPEATING_VALUE = "repeating_value"
        const val DATE_TZ = "date_tz"
    }
}
