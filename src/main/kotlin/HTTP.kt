package ru.xsrv.todo

import com.ucasoft.ktor.simpleCache.SimpleCache
import com.ucasoft.ktor.simpleCache.cacheOutput
import com.ucasoft.ktor.simpleMemoryCache.memoryCache
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

fun Application.configureHTTP() {
    install(CachingHeaders) {
        options { call, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                else -> null
            }
        }
    }
    install(ConditionalHeaders)
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    val isBehindProxy = environment.config.propertyOrNull("ktor.behind-proxy")?.getString().toBoolean()
    if(isBehindProxy) {
        install(ForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy
        install(XForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy
    }

    routing {// todo 20250602 secure
        openAPI(path = "openapi")
    }
    install(SimpleCache) {
        memoryCache {
            invalidateAt = 10.seconds
        }
    }
    routing {// todo 20250602 secure
        swaggerUI(path = "openapi")
    }
    routing {
        cacheOutput(2.seconds) {
            get("/short") {
                call.respond(Random.nextInt().toString())
            }
        }
        cacheOutput {
            get("/default") {
                call.respond(Random.nextInt().toString())
            }
        }
    }
}
