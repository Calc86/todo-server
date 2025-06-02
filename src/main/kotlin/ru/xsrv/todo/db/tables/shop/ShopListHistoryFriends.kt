package ru.xsrv.todo.ru.xsrv.todo.db.tables.shop

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users

/**
 * Делимся историей покупок с другом. Делим отдельно, так как есть завяка за комментарий/магазин
 */
object ShopListHistoryFriends : Table(Names.TABLE) {

    val history = reference(Names.HISTORY_ID, ShopListHistory.id, onDelete = ReferenceOption.CASCADE)
    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE)
    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime)
    val comment = text(Names.COMMENT).default("")

    override val primaryKey = PrimaryKey(history, user)

    private object Names {
        const val TABLE = "shop_list_history_friends"

        const val HISTORY_ID = "history_id"
        const val USER_ID = "user_id"
        const val DATE = "date"
        const val COMMENT = "comment"
    }
}

