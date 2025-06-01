@file:Suppress("PropertyName")

package ru.xsrv.todo.ru.xsrv.todo.models

import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users
import java.util.*

data class Register(
    val name: String,
    val email: String,
    val password: String,
    val device: Users.Device,
    val device_id: UUID,
)
