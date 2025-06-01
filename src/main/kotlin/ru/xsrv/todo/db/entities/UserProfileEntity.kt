package ru.xsrv.todo.ru.xsrv.todo.db.entities

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.UsersProfiles

class UserProfileEntity(id: EntityID<Int>): Entity<Int>(id) {
    companion object: EntityClass<Int, UserProfileEntity>(UsersProfiles)

    val name by UsersProfiles.name
}
