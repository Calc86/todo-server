package ru.xsrv.todo.ru.xsrv.todo.ktor.action

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.xsrv.todo.ru.xsrv.todo.ktor.JWTConfig
import ru.xsrv.todo.ru.xsrv.todo.ktor.UserPrincipal
import ru.xsrv.todo.ru.xsrv.todo.ktor.validate
import ru.xsrv.todo.ru.xsrv.todo.models.requests.Auth
import ru.xsrv.todo.ru.xsrv.todo.services.UserService
import java.util.*

fun Route.userLogin(
    path: String,
    userService: UserService,
    jwt: JWTConfig,
): Route = post(path) {
    val auth = call.receive<Auth>()
    auth.validate(this, Auth.validator) {
        // Check username and password
        val user = userService.selectUser(auth.email!!, auth.password!!) ?: run {
            call.respond(UnauthorizedResponse())
            return@validate
        }

        // todo 20250602 chec user exists
        // todo 20250601 create session

        val token = JWT.create()
            .withAudience(jwt.audience)
            .withIssuer(jwt.issuer)
            .withClaim(UserPrincipal.CLAIM_EMAIL, user.email)
            .withClaim(UserPrincipal.CLAIM_SID, "any")
            .withClaim(UserPrincipal.CLAIM_ID, user.id)
            .withExpiresAt(Date(System.currentTimeMillis() + jwt.ttl * 1000))
            .sign(Algorithm.HMAC256(jwt.secret))
        call.respond(
            hashMapOf(
                "token" to token,
                "ttl" to jwt.ttl,
            )
        )
    }
}
