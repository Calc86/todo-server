package ru.xsrv.todo.ru.xsrv.todo.ktor.action

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.xsrv.todo.ru.xsrv.todo.db.tables.shop.ShopLists
import ru.xsrv.todo.ru.xsrv.todo.ktor.UserPrincipal
import ru.xsrv.todo.ru.xsrv.todo.ktor.exceptions.HttpException
import ru.xsrv.todo.ru.xsrv.todo.ktor.validate
import ru.xsrv.todo.ru.xsrv.todo.models.ShopList
import ru.xsrv.todo.ru.xsrv.todo.services.ShopListService

fun Route.listCreate(
    path: String,
    listService: ShopListService,
): Route = post(path) {

    val principal = call.principal<UserPrincipal>()!!
    val list = call.receive<ShopList>()
    list.validate(this, ShopList.validator) {
        val result = listService.create(principal.user.id, list)
        call.respond(result)
    }
}


fun Route.listUpdate(
    path: String,
    listService: ShopListService,
): Route = post(path) {
    val principal = call.principal<UserPrincipal>()!!
    val list = call.receive<ShopList>()
    list.validate(this, ShopList.validator) {
        val result = listService.update(principal.user.id, list)
        call.respond(result)
    }
}


fun Route.listDone(
    path: String,
    listService: ShopListService,
): Route = post(path) {
    val principal = call.principal<UserPrincipal>()!!
    val list = call.receive<ShopList>() // todo 20250602 may be int id
    list.copy(status = ShopLists.Status.DONE).validate(this, ShopList.validator) {
        val result = listService.update(principal.user.id, list)
        call.respond(result)
    }
}


fun Route.listDelete(
    path: String,
    listService: ShopListService,
): Route = post(path) {
    val principal = call.principal<UserPrincipal>()!!
    val list = call.receive<ShopList>() // todo 20250602 may be int id
    listService.selectEntity(principal.user.id, list)
        ?.delete()
        ?: throw HttpException(HttpStatusCode.NotFound, "ShopList with id ${list.id} not found for current user")
    call.respond(HttpStatusCode.OK)
}
