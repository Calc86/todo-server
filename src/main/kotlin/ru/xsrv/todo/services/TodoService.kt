package ru.xsrv.todo.ru.xsrv.todo.services

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.xsrv.todo.ru.xsrv.todo.db.DBException
import ru.xsrv.todo.ru.xsrv.todo.db.UnknownInsertErrorException
import ru.xsrv.todo.ru.xsrv.todo.db.entities.TodoDoneEntity
import ru.xsrv.todo.ru.xsrv.todo.db.entities.TodoEntity
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.TodoDone
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.Todos
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users
import ru.xsrv.todo.ru.xsrv.todo.models.Todo
import kotlin.coroutines.CoroutineContext

class TodoService(
    database: Database,
    private val context: CoroutineContext = Dispatchers.IO,
) {
    init {
        transaction(database) {
            SchemaUtils.create(Todos)
            SchemaUtils.create(TodoDone)
        }
    }

    suspend fun create(userId: Int, todo: Todo, tags: List<String> = listOf()) = dbQuery {
        val userEntity =
            UserEntity.findById(userId) ?: throw UnknownInsertErrorException("UserEntity not found for id $userId")
        // todo 20250602 add tags
        TodoEntity.new {
            this.user = userEntity.id
            // todo 20250602 other fields
            title = todo.title
            description = todo.description
        }.let { Todo.entityMapper(it) }
    }

    suspend fun select(todo: Todo): Todo? = dbQuery {
        TodoEntity.findById(todo.id)?.let { Todo.entityMapper(it) }
    }

    suspend fun selectEntity(userId: Int, todo: Todo): TodoEntity? = selectEntity(userId, todo.id)

    suspend fun selectEntity(userId: Int, id: Int): TodoEntity? = dbQuery {
        TodoEntity.findById(id)?.let {
            when (it.user.value == userId) {
                true -> it
                false -> null
            }
        }
    }

    suspend fun select(userId: Int, todo: Todo): Todo? = dbQuery {
        selectEntity(userId, todo)?.let { Todo.entityMapper(it) }
    }

    suspend fun update(userId: Int, todo: Todo): Todo = dbQuery {
        (TodoEntity.findSingleByAndUpdate((Todos.user eq userId) and (Todos.id eq todo.id)) {
            it.title = todo.title
            it.description = todo.description
            it.status = todo.status
            // todo 20250607 add other fields
            // todo 20250602 status DONE todo routine for some more logic
        } ?: throw DBException("entity not found or not your entity"))
            .let { Todo.entityMapper(it) }
    }

    suspend fun done(userId: Int, id: Int, comment: String = "") = dbQuery {
        (TodoEntity.findByIdAndUpdate(id) {
            it.status = Todos.Status.DONE
        } ?: throw DBException("TodoEntity not found for id $id")).also { todo ->
            TodoDoneEntity.new {
                this.todo = todo.id
                // todo 20250602 repeating id
                this.comment = comment
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Todos.deleteWhere { Users.id.eq(id) }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(context) { block() }
}