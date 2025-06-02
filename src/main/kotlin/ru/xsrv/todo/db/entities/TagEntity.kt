package ru.xsrv.todo.ru.xsrv.todo.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.xsrv.todo.ru.xsrv.todo.db.tables.Tags

class TagEntity(id: EntityID<Int>) : IntEntity(id)  {
    companion object : IntEntityClass<TagEntity>(Tags)

    // todo 20250602 возможно надо добавить users_id или оставить всё как есть с ограниченным набором категорий
    var name by Tags.name
    var description by Tags.description
}
