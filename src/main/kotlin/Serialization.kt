package ru.xsrv.todo

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.reflect.Type
import java.time.LocalDateTime

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            registerTypeAdapter(LocalDateTime::class.java, TimeSerializer())
        }
//        jackson {
//            registerModule(JavaTimeModule())
//        }

    }
    routing {
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}


class TimeSerializer : JsonSerializer<LocalDateTime> {
    override fun serialize(src: LocalDateTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
//        return JsonPrimitive(src.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        return JsonPrimitive(src.toString())    // todo 20250602

//        return JsonPrimitive("123")    }
    }
}
