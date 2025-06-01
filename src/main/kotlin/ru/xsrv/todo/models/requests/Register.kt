@file:Suppress("PropertyName")

package ru.xsrv.todo.ru.xsrv.todo.models.requests

import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users
import ru.xsrv.todo.ru.xsrv.todo.db.validateEnum
import ru.xsrv.todo.ru.xsrv.todo.db.validateNotBlankOrNull
import ru.xsrv.todo.ru.xsrv.todo.db.validateUUID

data class Register(
    val name: String?,
    val email: String?,
    val password: String?,
    val device: String?,
    val device_id: String?,
) {
    companion object {
        val validator: Validator<Register> = { register ->
            register.name.validateNotBlankOrNull("name")
            register.email.validateNotBlankOrNull("email")
            register.password.validateNotBlankOrNull("password")
            register.device.validateNotBlankOrNull("device")
            register.device_id.validateNotBlankOrNull("device_id")

            // todo 20250601 validate email
            register.device.validateEnum(Users.Device.entries, "device")
            register.device_id.validateUUID("device_id")
        }
    }
}
