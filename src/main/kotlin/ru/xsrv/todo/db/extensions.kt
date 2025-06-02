package ru.xsrv.todo.ru.xsrv.todo.db

import org.mindrot.jbcrypt.BCrypt
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.ValidationException
import kotlin.enums.EnumEntries

const val VAR_CHAR_MAX_LENGTH = 255

typealias Mapper<T, R> = (T) -> R

class UnknownInsertErrorException(message: String? = null) : Exception("Unknown InsertErrorException ${message ?: ""}")

class DBException(message: String? = null) : Exception("DB exception ${message ?: ""}")

fun <T> Boolean.returnOnTrueOrNull(data: T): T? = when(this) {
    true -> data
    false -> null
}

fun String.hashPassword(): String = BCrypt.hashpw(this, BCrypt.gensalt())

fun String.checkPassword(hashed: String?) = when(hashed.isNullOrEmpty()) {
    true -> false
    false -> BCrypt.checkpw(this, hashed)
}

fun Boolean.falseAsNull(): Boolean? = if(this) true else null

val uuidRegEx = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$".toRegex()

fun <E : Enum<E>> String?.validateEnum(ee: EnumEntries<E>, field: String) {
    this?.let { v ->
        ee.map { it.toString() }.contains(v.uppercase()).falseAsNull()
    } ?: throw ValidationException("$field is not in range: " + ee.joinToString(", ") { it.toString().lowercase() })
}

fun String?.validateNotBlankOrNull(field: String) {
    if(this.isNullOrBlank()) throw ValidationException("$field is empty")
}

fun String?.validateUUID(field: String) {
    this?.let { uuid ->
        uuidRegEx.containsMatchIn(uuid).falseAsNull()
    } ?: throw ValidationException("$field is not uuid")
}
