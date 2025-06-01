package ru.xsrv.todo.ru.xsrv.todo.db.tables.shop

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object ShopListHistoryMarkets : IntIdTable(Names.TABLE) {

    val history = reference(Names.HISTORY_ID, ShopListHistory.id, onDelete = ReferenceOption.CASCADE)
    val market = text(Names.MARKET).default("")

    private object Names {
        const val TABLE = "shop_list_history_markets"

        const val HISTORY_ID = "history_id"
        const val MARKET = "market"
    }
}
