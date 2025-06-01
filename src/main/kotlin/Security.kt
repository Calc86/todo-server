package ru.xsrv.todo

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.auth.ldap.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject
import ru.xsrv.todo.ru.xsrv.todo.services.UserService
import ru.xsrv.todo.ru.xsrv.todo.ktor.JWTConfig
import ru.xsrv.todo.ru.xsrv.todo.ktor.UserPrincipal

fun Application.configureSecurity() {
//    val secret = environment.config.property("jwt.secret").getString()
//    val issuer = environment.config.property("jwt.domain").getString()
//    val audience = environment.config.property("jwt.audience").getString()
//    val myRealm = environment.config.property("jwt.realm").getString()

//    authentication {
//
//        jwt {
//
//        }
//    }

    // default generated bellow
    authentication {
        basic(name = "myauth1") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == credentials.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }

        form(name = "myauth2") {
            userParamName = "user"
            passwordParamName = "password"
            challenge {
                /**/
            }
        }
    }

    // Please read the jwt property from the config file if you are using EngineMain
    val jwt by inject<JWTConfig>()
    val userService by inject<UserService>()
    authentication {
        jwt {
            realm = jwt.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwt.secret))
                    .withAudience(jwt.audience)
                    .withIssuer(jwt.issuer)
                    .build()
            )
            validate { credential ->
                // todo 20250602 check is session exists
                // todo 20250602 check is session closed
                val userId = credential.payload.getClaim(UserPrincipal.CLAIM_ID).asInt()
                val user = userService.selectUser(userId)!!
//                if (credential.payload.audience.contains(jwt.audience)) JWTPrincipal(credential.payload) else null
                if (credential.payload.audience.contains(jwt.audience)) UserPrincipal(user, null, credential.payload) else null
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }


    val localhost = "http://0.0.0.0"
    val ldapServerPort = 6998 // TODO: change to real value!
    authentication {
        basic("authName") {
            realm = "realm"
            validate { credential ->
                ldapAuthenticate(credential, "ldap://$localhost:${ldapServerPort}", "uid=%s,ou=system")
            }
        }
    }
    authentication {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = HttpClient(Apache)
        }
    }
    routing {
        authenticate("myauth1") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
        authenticate("myauth2") {
            get("/protected/route/form") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
        authenticate("auth-oauth-google") {
            get("login") {
                call.respondRedirect("/callback")
            }

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                call.sessions.set(UserSession(principal?.accessToken.toString()))
                call.respondRedirect("/hello")
            }
        }
    }
}

class UserSession(accessToken: String)
