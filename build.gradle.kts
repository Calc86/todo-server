val exposed_version: String by project
val h2_version: String by project
val koin_version: String by project
val kotlin_version: String by project
val kotlinx_html_version: String by project
val logback_version: String by project
val prometheus_version: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

group = "ru.xsrv.todo"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
    maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
    implementation("io.ktor:ktor-server-caching-headers")
    implementation("io.ktor:ktor-server-conditional-headers")
    implementation("io.ktor:ktor-server-default-headers")
    implementation("io.ktor:ktor-server-forwarded-header")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-openapi")
    implementation("com.ucasoft.ktor:ktor-simple-cache:0.53.4")
    implementation("com.ucasoft.ktor:ktor-simple-memory-cache:0.53.4")
    implementation("io.ktor:ktor-server-swagger")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation("io.ktor:ktor-server-auth-ldap")
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-apache")
    implementation("io.ktor:ktor-server-host-common")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-call-id")
    implementation("io.ktor:ktor-server-metrics")
    implementation("io.ktor:ktor-server-metrics-micrometer")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometheus_version")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-gson")
    implementation("io.ktor:ktor-server-html-builder")
    implementation("org.jetbrains.kotlinx:kotlinx-html:$kotlinx_html_version")
    implementation("org.jetbrains:kotlin-css:1.0.0-pre.129-kotlin-1.4.20")
    implementation("io.ktor:ktor-server-pebble")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.github.flaxoos:ktor-server-rate-limiting:2.2.1")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
