package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.Mapper
import ru.xsrv.todo.ru.xsrv.todo.db.entities.ShopListEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists

@Serializable
data class ShopList(
    val id: Int,
    val title: String,
    val description: String,
    val unit: ShopLists.Units,
    val unitValue: Float,
    val date: String,
    val status: ShopLists.Status,
    val comment: String,
) {
    companion object {
        val entityMapper: Mapper<ShopListEntity, ShopList> = { list ->
            ShopList(
                list.id.value,
                list.title,
                list.description,
                list.unit,
                list.unitValue,
                list.date.toString(),
                list.status,
                list.comment
            )
        }
    }
}
