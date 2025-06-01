package ru.xsrv.todo.ru.xsrv.todo.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists

class ShopListEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ShopListEntity>(ShopLists)

    var user by UserEntity referencedOn ShopLists.user
    var list by ShopLists.list
    var title by ShopLists.title
    var description by ShopLists.description
    var unit by ShopLists.unit
    var unitValue by ShopLists.unitValue
    var date by ShopLists.date
    var status by ShopLists.status
    var comment by ShopLists.comment

    // todo 20250602 relations
}