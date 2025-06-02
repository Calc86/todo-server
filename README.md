# todo-server

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Caching Headers](https://start.ktor.io/p/caching-headers)             | Provides options for responding with standard cache-control headers                |
| [Conditional Headers](https://start.ktor.io/p/conditional-headers)     | Skips response body, depending on ETag and LastModified headers                    |
| [Default Headers](https://start.ktor.io/p/default-headers)             | Adds a default set of headers to HTTP responses                                    |
| [Forwarded Headers](https://start.ktor.io/p/forwarded-header-support)  | Allows handling proxied headers (X-Forwarded-*)                                    |
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [OpenAPI](https://start.ktor.io/p/openapi)                             | Serves OpenAPI documentation                                                       |
| [Simple Cache](https://start.ktor.io/p/simple-cache)                   | Provides API for cache management                                                  |
| [Simple Memory Cache](https://start.ktor.io/p/simple-memory-cache)     | Provides memory cache for Simple Cache plugin                                      |
| [Swagger](https://start.ktor.io/p/swagger)                             | Serves Swagger UI for your project                                                 |
| [Authentication](https://start.ktor.io/p/auth)                         | Provides extension point for handling the Authorization header                     |
| [Authentication Basic](https://start.ktor.io/p/auth-basic)             | Handles 'Basic' username / password authentication scheme                          |
| [Authentication JWT](https://start.ktor.io/p/auth-jwt)                 | Handles JSON Web Token (JWT) bearer authentication scheme                          |
| [Authentication LDAP](https://start.ktor.io/p/auth-ldap)               | Handles Lightweight Directory Access Protocol (LDAP) authentication                |
| [Authentication OAuth](https://start.ktor.io/p/auth-oauth)             | Handles OAuth Bearer authentication scheme                                         |
| [Static Content](https://start.ktor.io/p/static-content)               | Serves static files from defined locations                                         |
| [Status Pages](https://start.ktor.io/p/status-pages)                   | Provides exception handling for routes                                             |
| [Call Logging](https://start.ktor.io/p/call-logging)                   | Logs client requests                                                               |
| [Call ID](https://start.ktor.io/p/callid)                              | Allows to identify a request/call.                                                 |
| [Metrics](https://start.ktor.io/p/metrics)                             | Adds supports for monitoring several metrics                                       |
| [Micrometer Metrics](https://start.ktor.io/p/metrics-micrometer)       | Enables Micrometer metrics in your Ktor server application.                        |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [GSON](https://start.ktor.io/p/ktor-gson)                              | Handles JSON serialization using GSON library                                      |
| [HTML DSL](https://start.ktor.io/p/html-dsl)                           | Generates HTML from Kotlin DSL                                                     |
| [CSS DSL](https://start.ktor.io/p/css-dsl)                             | Generates CSS from Kotlin DSL                                                      |
| [Pebble](https://start.ktor.io/p/pebble)                               | Allows you to use Pebble templates as views within your application                |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Exposed](https://start.ktor.io/p/exposed)                             | Adds Exposed database to your application                                          |
| [Koin](https://start.ktor.io/p/koin)                                   | Provides dependency injection                                                      |
| [Rate Limiting](https://start.ktor.io/p/ktor-server-rate-limiting)     | Manage request rate limiting as you see fit                                        |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

## Описание

Запускать в Idea

./tmp/db данные по базе данных

./data/data.db SQlite база данных (можно удалять для тестов с нуля)

./kotlin/Administration.kt запуск сервера с java 17

mm.png заложено в БД, но не реализовано пока

./_preview запросы HTTP из idea для теста API

Реализовано
- Регистрация
- Авторизация
- Получение токена
- Получение refresh токена
- обновление токенов
- получение профиля
- Прописаны основные Entities для работы с БД
- немного админ панели, чтобы не лезть в базу
- всякие todo
