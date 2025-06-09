package ru.xsrv.todo.ru.xsrv.todo.di

import io.ktor.server.application.*
import org.koin.dsl.module
import ru.xsrv.todo.ru.xsrv.todo.ktor.BasicAdminConfig
import ru.xsrv.todo.ru.xsrv.todo.ktor.BasicDocConfig
import ru.xsrv.todo.ru.xsrv.todo.ktor.Constants
import ru.xsrv.todo.ru.xsrv.todo.ktor.JWTConfig

fun Application.koinSecret() = module {
    single {
        JWTConfig(
            secret = environment.config.property("jwt.secret").getString(),
            issuer = environment.config.property("jwt.domain").getString(),
            audience = environment.config.property("jwt.audience").getString(),
            realm = environment.config.property("jwt.realm").getString(),
            ttl = environment.config.property("jwt.ttl").getString().toInt(),
            refreshTtl = environment.config.property("jwt.refresh-ttl").getString().toInt()
        )
    }
    single {
        BasicAdminConfig(
            user = environment.config.property(Constants.Authentication.ADMIN_USER).getString(),
            password = environment.config.property(Constants.Authentication.ADMIN_PASSWORD).getString(),
        )
    }
    single {
        BasicDocConfig(
            user = environment.config.property(Constants.Authentication.DOC_USER).getString(),
            password = environment.config.property(Constants.Authentication.DOC_PASSWORD).getString(),
        )
    }
}
