package ru.xsrv.todo.ru.xsrv.todo.ktor

import com.auth0.jwt.interfaces.Payload
import ru.xsrv.todo.ru.xsrv.todo.models.User

data class UserPrincipal(
    val user: User,
    val session: Any? = null,   // todo 20250602
    val payload: Payload
) {
    companion object {
        const val CLAIM_EMAIL = "email";
        const val CLAIM_SID = "sid";
        const val CLAIM_ID = "id";
    }
}
