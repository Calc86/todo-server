package ru.xsrv.todo.ru.xsrv.todo.db.tables.shop

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.xsrv.todo.ru.xsrv.todo.db.VAR_CHAR_MAX_LENGTH

/**
 * Картинка того, что надо купить
 */
object ShopListImages : IntIdTable(Names.TABLE) {

    val list = reference(Names.LIST_ID, ShopLists.id, onDelete = ReferenceOption.CASCADE)
    val image = varchar(Names.IMAGE, length = VAR_CHAR_MAX_LENGTH)

    private object Names {
        const val TABLE = "shop_list_images"

        const val LIST_ID = "list_id"
        const val IMAGE = "image"

    }
}
