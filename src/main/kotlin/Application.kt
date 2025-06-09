package ru.xsrv.todo

import io.ktor.server.application.*
import ru.xsrv.todo.ru.xsrv.todo.ktor.configureApi
import ru.xsrv.todo.ru.xsrv.todo.ktor.configureWebAdmin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureFrameworks()
    configureSecurity()
    configureHTTP()
//    configureMonitoring()
    configureSerialization()
    configureTemplating()
    configureDatabases()

    configureAdministration()
    configureRouting()
    configureApi()
    configureWebAdmin()
}
