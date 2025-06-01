package ru.xsrv.todo.ru.xsrv.todo.db.tables.shop

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.tables.VAR_CHAR_MAX_LENGTH
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users

object ShopList : IntIdTable(Names.TABLE) {

    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE)
    val list = reference(Names.LIST_ID, ShopList.id, onDelete = ReferenceOption.CASCADE)
    val title = varchar(Names.TITLE, length = VAR_CHAR_MAX_LENGTH)
    val description = text(Names.DESCRIPTION)
    val unit = enumeration(Names.UNIT, Units::class)
    val unitValue = float(Names.UNIT_VALUE).default(1f)
    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime).index()
    val status = enumeration(Names.STATUS, Status::class).index()
    val comment = text(Names.COMMENT).default("")

    enum class Units {
        ITEM,
        KG,
        GR
    }

    enum class Status {
        DONE,
        NEW,
        REPEATED,
        DELAY
    }

    private object Names {
        const val TABLE = "shop_lists"

        const val USER_ID = "user_id"
        const val LIST_ID = "list_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val UNIT = "unit"
        const val UNIT_VALUE = "unit_value"
        const val DATE = "date"
        const val STATUS = "status"
        const val COMMENT = "comment"
    }
}
