package ru.xsrv.todo.ru.xsrv.todo.models

import kotlinx.serialization.Serializable
import ru.xsrv.todo.ru.xsrv.todo.db.Mapper
import ru.xsrv.todo.ru.xsrv.todo.db.entities.UserEntity
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users

@Serializable
data class User(
    val id: Int,
    val uuid: String,
    val email: String,
    val date: String,
    val status: Users.Status,
    val profile: UserProfile
) {
    companion object {
        val entityMapper: Mapper<UserEntity, User> = { user ->
            User(
                user.id.value,
                user.uuid.toString(),
                user.email,
                user.date.toString(),
                user.status,
                UserProfile.entityMapper(user.profile.first())
            )
        }
    }
}
