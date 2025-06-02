package ru.xsrv.todo.ru.xsrv.todo.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.xsrv.todo.ru.xsrv.todo.db.tables.user.UserSessions

class UserSessionEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserSessionEntity>(UserSessions)

    var userId by UserSessions.user
    var date by UserSessions.date
    var closed by UserSessions.closed
    var device by UserSessions.device
    var deviceId by UserSessions.device_id
    var pushType by UserSessions.pushType
    var push by UserSessions.push

    //val user by UserEntity referencedOn UserSessions.id //InvocationTargetException

}
