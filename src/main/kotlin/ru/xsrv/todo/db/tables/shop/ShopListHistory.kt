package ru.xsrv.todo.ru.xsrv.todo.db.tables.shop

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.VAR_CHAR_MAX_LENGTH
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists.Units

object ShopListHistory : IntIdTable(Names.TABLE) {

    /** история элемента, создаем при завершении покупки */
    val list = reference(Names.LIST_ID, ShopLists.id, onDelete = ReferenceOption.CASCADE)
    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime).index()

    /** можно сохрнаить штрихкод */
    val barcode_type = varchar(Names.BARCODE_TYPE, length = VAR_CHAR_MAX_LENGTH).nullable().default(null)
    val barcode = varchar(Names.BARCODE, length = VAR_CHAR_MAX_LENGTH).nullable().default(null)

    /** сохраняе вес/количество */
    val unit = enumeration(Names.UNIT, Units::class)
    val unitValue = float(Names.UNIT_VALUE).default(1f)

    /** сохраняем цену */
    val price_unit = enumeration(Names.PRICE_UNIT, PriceUnit::class).nullable().default(null)
    val price = float(Names.PRICE).nullable().default(null)

    val comment = text(Names.COMMENT).default("")

    enum class PriceUnit {
        RUB
    }

    private object Names {
        const val TABLE = "shop_list_history"

        const val LIST_ID = "list_id"
        const val DATE = "date"
        const val BARCODE_TYPE = "barcode_type"
        const val BARCODE = "barcode"
        const val UNIT = "unit"
        const val UNIT_VALUE = "unit_value"
        const val PRICE_UNIT = "price_unit"
        const val PRICE = "price"
        const val COMMENT = "comment"
    }
}
