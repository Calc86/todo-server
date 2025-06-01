package ru.xsrv.todo.ru.xsrv.todo.db.tables.shop

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import ru.xsrv.todo.ru.xsrv.todo.db.tables.Tags

object ShopListTags : Table(Names.TABLE) {

    val list = reference(Names.LIST_ID, ShopLists.id, onDelete = ReferenceOption.CASCADE)
    val tag = reference(Names.TAG_ID, Tags.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(list, tag)

    private object Names {
        const val TABLE = "shop_list_tags"

        const val LIST_ID = "list_id"
        const val TAG_ID = "tag_id"
    }
}

