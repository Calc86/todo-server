package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.Mapper
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserProfileEntity

@Serializable
data class UserProfile(
    val name: String
) {
    companion object {
        val entityMapper: Mapper<UserProfileEntity, UserProfile> = { profile ->
            UserProfile(profile.name)
        }
    }
}
