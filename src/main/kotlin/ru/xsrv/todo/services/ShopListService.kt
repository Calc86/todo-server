package ru.xsrv.todo.ru.xsrv.todo.services

import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.xsrv.todo.ru.xsrv.todo.db.DBException
import ru.xsrv.todo.ru.xsrv.todo.db.UnknownInsertErrorException
import ru.xsrv.todo.ru.xsrv.todo.db.entities.ShopListEntity
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopListHistory
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users
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
            date = list.date?.let{LocalDateTime.parse(it)} ?: LocalDateTime.parse("2022-01-01T00:00:00", LocalDateTime.Formats.ISO)   // todo 20250602 check
            status = list.status
            comment = list.comment
            // todo 20250602 other fields
        }.let { ShopList.entityMapper(it) }
    }

    suspend fun select(list: ShopList, tags: List<String> = listOf()) = dbQuery {
        ShopListEntity.findById(list.id)?.let { ShopList.entityMapper(it) }
    }

    suspend fun selectEntity(userId: Int, list: ShopList): ShopListEntity? = selectEntity(userId, list.id)

    suspend fun selectEntity(userId: Int, id: Int): ShopListEntity? = dbQuery {
        ShopListEntity.findById(id)?.let {
            when (it.user.value == userId) {
                true -> it
                false -> null
            }
        }
    }

    suspend fun select(userId: Int, list: ShopList): ShopList? = dbQuery {
        selectEntity(userId, list)?.let { ShopList.entityMapper(it) }
    }

    suspend fun update(userId: Int, list: ShopList): ShopList = dbQuery {
        (ShopListEntity.findSingleByAndUpdate((ShopLists.user eq userId) and (ShopLists.id eq list.id)) {
            it.title = list.title
            it.description = list.description
            it.unit = list.unit
            it.unitValue = list.unitValue
            it.date = list.date?.let{LocalDateTime.parse(it)} ?: LocalDateTime.parse("2022-01-01T00:00:00", LocalDateTime.Formats.ISO)   // todo 20250602 check
            it.status = list.status
            it.comment = list.comment
            // todo 20250614 other fields
        } ?: throw DBException("entity not found or not your entity"))
            .let { ShopList.entityMapper(it) }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            ShopLists.deleteWhere { Users.id.eq(id) }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(context) { block() }
}
