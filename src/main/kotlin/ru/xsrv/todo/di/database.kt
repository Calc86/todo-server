package ru.xsrv.todo.ru.xsrv.todo.di

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.koin.dsl.module
import ru.xsrv.todo.ru.xsrv.todo.services.UserService
import ru.xsrv.todo.ru.xsrv.todo.ktor.DBConfig
import java.sql.Connection.TRANSACTION_SERIALIZABLE

fun Application.koinDatabase() = module {
    single {
        DBConfig(
            url = environment.config.property("db.url").getString(),
            driver = environment.config.property("db.driver").getString(),
            user = environment.config.property("db.user").getString(),
            password = environment.config.property("db.password").getString(),
        )
    }
    single<Database> {
        val dbConfig: DBConfig = get()
        Database.connect(
            url = dbConfig.url,
            driver = dbConfig.driver,
            user = dbConfig.user,
            password = dbConfig.password,
        ).also {
            if (dbConfig.isSQLite) TransactionManager.manager.defaultIsolationLevel = TRANSACTION_SERIALIZABLE
        }
    }

    single { UserService(get()) }
}