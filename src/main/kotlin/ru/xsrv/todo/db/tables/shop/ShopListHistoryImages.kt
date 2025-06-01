package ru.xsrv.todo.ru.xsrv.todo.db.tables.shop

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.xsrv.todo.ru.xsrv.todo.db.VAR_CHAR_MAX_LENGTH

object ShopListHistoryImages : IntIdTable(Names.TABLE) {

    val history = reference(Names.HISTORY_ID, ShopList.id, onDelete = ReferenceOption.CASCADE)
    val image = varchar(Names.IMAGE, length = VAR_CHAR_MAX_LENGTH)

    private object Names {
        const val TABLE = "shop_list_history_images"

        const val HISTORY_ID = "history_id"
        const val IMAGE = "image"
    }
}
