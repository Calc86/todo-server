package ru.xsrv.todo.ru.xsrv.todo.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.Users
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.UsersProfiles

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    val uuid by Users.uuid
    val date by Users.date

    var email by Users.email
    var password by Users.password
    var status by Users.status
    val profile by UserProfileEntity referrersOn UsersProfiles.user
}
