package ru.xsrv.todo

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.xsrv.todo.ru.xsrv.todo.di.koinDatabase
import ru.xsrv.todo.ru.xsrv.todo.di.koinSecret

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<HelloService> {
                HelloService {
                    println(environment.log.info("Hello, World!"))
                }
            }
        } + koinDatabase() + koinSecret())
    }
}
