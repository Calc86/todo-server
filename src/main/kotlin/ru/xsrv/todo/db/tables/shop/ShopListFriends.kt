package ru.xsrv.todo.ru.xsrv.todo.db.tables.shop

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users

object ShopListFriends : Table(Names.TABLE) {

    /** Какой элемент передаем другу */
    val list = reference(Names.LIST_ID, ShopLists.id, onDelete = ReferenceOption.CASCADE)
    /** Друг */
    val user = reference(Names.USER_ID, Users.id, onDelete = ReferenceOption.CASCADE)
    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime)
    val comment = text(Names.COMMENT).default("")

    override val primaryKey = PrimaryKey(list, user)

    private object Names {
        const val TABLE = "shop_list_friends"

        const val LIST_ID = "list_id"
        const val USER_ID = "user_id"
        const val DATE = "date"
        const val COMMENT = "comment"
    }
}

