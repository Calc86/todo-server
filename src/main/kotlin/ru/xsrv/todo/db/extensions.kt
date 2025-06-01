package ru.xsrv.todo.ru.xsrv.todo.db

import org.mindrot.jbcrypt.BCrypt

const val VAR_CHAR_MAX_LENGTH = 255

typealias Mapper<T, R> = (T) -> R

class UnknownInsertErrorException(message: String? = null) : Exception("Unknown InsertErrorException ${message ?: ""}")

fun <T> Boolean.returnOnTrueOrNull(data: T): T? = when(this) {
    true -> data
    false -> null
}

fun String.hashPassword(): String = BCrypt.hashpw(this, BCrypt.gensalt())

fun String.checkPassword(hashed: String?) = when(hashed.isNullOrEmpty()) {
    true -> false
    false -> BCrypt.checkpw(this, hashed)
}
