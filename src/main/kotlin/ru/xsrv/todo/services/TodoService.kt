package ru.xsrv.todo.ru.xsrv.todo.services

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

class TodoService(
    database: Database,
    private val context: CoroutineContext = Dispatchers.IO,
) {

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(context) { block() }
}