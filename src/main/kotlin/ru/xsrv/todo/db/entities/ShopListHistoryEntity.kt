package ru.xsrv.todo.ru.xsrv.todo.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopListHistory

class ShopListHistoryEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ShopListHistoryEntity>(ShopListHistory)

    var list by ShopListHistory.list
    var date by ShopListHistory.date

    var barcodeType by ShopListHistory.barcode_type
    var barcode by ShopListHistory.barcode
    var unit by ShopListHistory.unit
    var unitValue by ShopListHistory.unitValue
    var priceUnit by ShopListHistory.price_unit
    var price by ShopListHistory.price
    var comment by ShopListHistory.comment

    // todo 20250602 add relations
}
