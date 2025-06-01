package ru.xsrv.todo

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureTemplating()
    configureDatabases()
    configureFrameworks()
    configureAdministration()
    configureRouting()
}
