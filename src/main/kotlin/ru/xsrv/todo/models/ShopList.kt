package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.Mapper
import ru.xsrv.todo.ru.xsrv.todo.db.entities.ShopListEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists
import ru.xsrv.todo.ru.xsrv.todo.models.requests.Validator

@Serializable
data class ShopList(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val unit: ShopLists.Units = ShopLists.Units.ITEM,
    val unitValue: Float = 1f,
    val date: String? = null,
    val status: ShopLists.Status = ShopLists.Status.NEW,
    val comment: String = "",
) {
    companion object {
        val entityMapper: Mapper<ShopListEntity, ShopList> = { list ->
            ShopList(
                id = list.id.value,
                title = list.title,
                description = list.description,
                unit = list.unit,
                unitValue = list.unitValue,
                date = list.date.toString(),
                status = list.status,
                comment = list.comment
            )
        }

        val validator: Validator<ShopList> = { shopList ->
            // todo 20250602 validate
        }
    }
}
