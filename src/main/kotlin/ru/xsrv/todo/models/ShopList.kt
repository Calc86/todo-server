package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists

@Serializable
data class ShopList(
    val id: Int,
    val title: String,
    val description: String,
    val unit: ShopLists.Units,
    val unitValue: Float,
    val dateTime: String,
    val status: ShopLists.Status,
    val comments: String,
)
