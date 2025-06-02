package ru.xsrv.todo.ru.xsrv.todo.services

import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.xsrv.todo.ru.xsrv.todo.db.UnknownInsertErrorException
import ru.xsrv.todo.ru.xsrv.todo.db.entities.ShopListEntity
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopListHistory
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists
import ru.xsrv.todo.ru.xsrv.todo.models.ShopList
import kotlin.coroutines.CoroutineContext

class ShopListService(
    database: Database,
    private val context: CoroutineContext = Dispatchers.IO,
) {
    init {
        transaction(database) {
            SchemaUtils.create(ShopLists)
            SchemaUtils.create(ShopListHistory)
        }
    }

    suspend fun create(userId: Int, list: ShopList, tags: List<String> = listOf()) = dbQuery {
        val userEntity =
            UserEntity.findById(userId) ?: throw UnknownInsertErrorException("UserEntity not found for id $userId")
        // todo 20250602 add tags
//        val id = EntityID(userId, ShopLists)
        ShopListEntity.new {
            this.user = userEntity.id
            // todo 20250602 list as null for repeating
            title = list.title
            description = list.description
            unit = list.unit
            unitValue = list.unitValue
            date = LocalDateTime.parse(list.date)   // todo 20250602 check
            status = list.status
            comment = list.comment
            // todo 20250602 other fields
        }.let { ShopList.entityMapper(it) }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(context) { block() }
}
