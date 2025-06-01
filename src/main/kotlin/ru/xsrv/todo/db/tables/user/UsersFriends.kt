package ru.xsrv.todo.ru.xsrv.todo.db.tables.user

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.xsrv.todo.ru.xsrv.todo.db.tables.VAR_CHAR_MAX_LENGTH

object UsersFriends : Table(Names.TABLE) {

    val user1 = UsersProfiles.reference(Names.USER_ID1, Users.id, onDelete = ReferenceOption.CASCADE)
    val user2 = UsersProfiles.reference(Names.USER_ID2, Users.id, onDelete = ReferenceOption.CASCADE)
    val date = datetime(Names.DATE).defaultExpression(CurrentDateTime).index()
    val title = varchar(Names.TITLE, length = VAR_CHAR_MAX_LENGTH).uniqueIndex()

    override val primaryKey = PrimaryKey(user1, user2)

    private object Names {
        const val TABLE = "users_friends"

        const val USER_ID1 = "user_id1"
        const val USER_ID2 = "user_id2"
        const val DATE = "date"
        const val TITLE = "title"
    }
}

