@file:Suppress("PropertyName")

package ru.xsrv.todo.ru.xsrv.todo.models.requests

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users
import ru.xsrv.todo.ru.xsrv.todo.db.validateEnum
import ru.xsrv.todo.ru.xsrv.todo.db.validateNotBlankOrNull
import ru.xsrv.todo.ru.xsrv.todo.db.validateUUID
import java.util.*

typealias Validator<T> = (T) -> Unit

@Serializable
data class Auth(
    val email: String?,
    val password: String?,
    val device: String?,
    val device_id: String?,
) {
    companion object {
        val validator: Validator<Auth> = { auth ->
            auth.email.validateNotBlankOrNull("email")
            auth.password.validateNotBlankOrNull("password")
            auth.device.validateNotBlankOrNull("device")
            auth.device_id.validateNotBlankOrNull("device_id")

            // todo 20250601 validate email
            auth.device.validateEnum(Users.Device.entries, "device")
            auth.device_id.validateUUID("device_id")
        }
    }

    val deviceType: Users.Device
        get() = Users.Device.valueOf(device!!.uppercase())

    val deviceId: UUID
        get() = UUID.fromString(device_id)
}
