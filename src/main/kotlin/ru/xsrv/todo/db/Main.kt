package ru.xsrv.todo.ru.xsrv.todo.db

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserEntity
import ru.xsrv.todo.ru.xsrv.todo.UserService

fun main() {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )
    val us = UserService(database)
    runBlocking {
        val r1 = us.selectUser(1)
        println(r1)
        val b = transaction {
            UserEntity.new {
                email = "a@a.ru"
            }
        }
        println(b.id)

        val c = us.registerByEmail("a@b.ru", "name", "12345")
        println(c.id)
        println(c.email)
        transaction {
            println(c.profile.first().name)
        }

        val user = us.selectUser(2)!!
        println(user.profile.name)

        val user2 = us.selectUser("a@b.ru", "12345" )
        println(user2!!.id)
    }
}
