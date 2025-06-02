package ru.xsrv.todo.ru.xsrv.todo.ktor

import com.auth0.jwt.interfaces.Payload
import ru.xsrv.todo.ru.xsrv.todo.models.User

data class UserPrincipal(
    val user: User,
    val session: Int,
    val payload: Payload
) {
    companion object {
        const val CLAIM_EMAIL = "email";
        const val CLAIM_SID = "sid";
        const val CLAIM_ID = "id";
        const val CLAIM_TYPE = "type";
    }

    enum class Types {
        AUTH, REFRESH
    }
}
