package ru.xsrv.todo.ru.xsrv.todo.ktor

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import ru.xsrv.todo.ru.xsrv.todo.db.entities.*
import ru.xsrv.todo.ru.xsrv.todo.db.tables.todo.Todos

fun Application.configureWebAdmin() {
    routing {
        authenticate(Constants.Authentication.ADMIN_AUTH) {
            route("/admin") {
                get {
//                    call.respondText("Hello, ${call.principal<UserIdPrincipal>()?.name}!")
                    call.respondHtml {
                        head {
                            link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                        }
                        body {
                            h1(classes = "page-title") {
                                +"Admin"
                            }
                            ul {
                                li { +"menu" }
                                li {
                                    a {
                                        href = "/admin/users"
                                        +"users"
                                    }
                                }
                                li {
                                    a {
                                        href = "/admin/sessions"
                                        +"sessions"
                                    }
                                }
                                li {
                                    a {
                                        href = "/admin/tags"
                                        +"tags"
                                    }
                                }
                                li {
                                    a {
                                        href = "/admin/todos"
                                        +"todos"
                                    }
                                }
                                li {
                                    a {
                                        href = "/admin/shop-lists"
                                        +"shop-lists"
                                    }
                                }
                            }
                        }
                    }
                }

                route("/users") {
                    get {
                        val users = transaction {
                            UserEntity.all().offset(0).limit(100).toList()
                        }

                        call.respondHtml {
                            head {
                                link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                            }
                            body {
                                h1(classes = "page-title") {
                                    +"Users"
                                }
                                table {
                                    tr {
                                        th { +"id" }
                                        th { +"uuid" }
                                        th { +"date" }
                                        th { +"email" }
                                        th { +"password" }
                                        th { +"status" }
                                        //th { +"profile" }
                                    }
                                    users.forEach {
                                        tr {
                                            td { +it.id.toString() }
                                            td { +it.uuid.toString() }
                                            td { +it.date.toString() }
                                            td { +it.email }
                                            td { +it.password.toString() }
                                            td { +it.status.toString() }
                                            //td { +it.profile.toString() }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                route("/sessions") {
                    get {
                        val sessions = transaction {
                            UserSessionEntity.all().offset(0).limit(100).toList()
                        }

                        call.respondHtml {
                            head {
                                link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                            }
                            body {
                                h1(classes = "page-title") {
                                    +"Sessions"
                                }
                                table {
                                    tr {
                                        th { +"id" }
                                        th { +"userId" }
                                        th { +"date" }
                                        th { +"closed" }
                                        th { +"device" }
                                        th { +"deviceId" }
                                        th { +"pushType" }
                                        th { +"push" }
                                    }
                                    sessions.forEach {
                                        tr {
                                            td { +it.id.toString() }
                                            td { +it.userId.toString() }
                                            td { +it.date.toString() }
                                            td { +it.closed.toString() }
                                            td { +it.device.toString() }
                                            td { +it.deviceId.toString() }
                                            td { +it.pushType.toString() }
                                            td { +it.push.toString() }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                route("/tags") {
                    get {
                        val tags = transaction {
                            TagEntity.all().offset(0).limit(100).toList()
                        }

                        call.respondHtml {
                            head {
                                link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                            }
                            body {
                                h1(classes = "page-title") {
                                    +"Tags"
                                }
                                table {
                                    tr {
                                        th { +"id" }
                                        th { +"name" }
                                        th { +"description" }
                                    }
                                    tags.forEach {
                                        tr {
                                            td { +it.id.toString() }
                                            td { +it.name }
                                            td { +it.description }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                route("/todos") {
                    get {
                        val todos = transaction {
                            TodoEntity.all().orderBy(Todos.id to SortOrder.DESC).offset(0).limit(100).toList()
                        }

                        call.respondHtml {
                            head {
                                link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                            }
                            body {
                                h1(classes = "page-title") {
                                    +"Todos"
                                }
                                table {
                                    tr {
                                        th { +"id" }
                                        th { +"user" }
                                        th { +"type" }
                                        th { +"repeating" }
                                        th { +"date1" }
                                        th { +"date2" }
                                        th { +"dateTZ" }
                                        th { +"title" }
                                        th { +"description" }
                                        th { +"status" }
                                        th { +"dateNext" }
                                    }
                                    todos.forEach {
                                        tr {
                                            td { +it.id.toString() }
                                            td { +it.user.value.toString() }
                                            td { +it.type.toString() }
                                            td { +it.repeating.toString() }
                                            td { +it.date1.toString() }
                                            td { +it.date2.toString() }
                                            td { +it.dateTZ.toString() }
                                            td { +it.title }
                                            td { +it.description }
                                            td { +it.status.toString() }
                                            td { +it.dateNext.toString() }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                route("/shop-lists") {
                    get {
                        val tags = transaction {
                            ShopListEntity.all().offset(0).limit(100).toList()
                        }

                        call.respondHtml {
                            head {
                                link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                            }
                            body {
                                h1(classes = "page-title") {
                                    +"Shop Lists"
                                }
                                table {
                                    tr {
                                        th { +"id" }
                                        th { +"user" }
                                        th { +"list" }
                                        th { +"title" }
                                        th { +"description" }
                                        th { +"unit" }
                                        th { +"unitValue" }
                                        th { +"date" }
                                        th { +"status" }
                                        th { +"comment" }
                                    }
                                    tags.forEach {
                                        tr {
                                            td { +it.id.toString() }
                                            td { +it.user.value.toString() }
                                            td { +it.list.value.toString() }
                                            td { +it.title }
                                            td { +it.description }
                                            td { +it.unit.toString() }
                                            td { +it.unitValue.toString() }
                                            td { +it.date.toString() }
                                            td { +it.status.toString() }
                                            td { +it.comment }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
