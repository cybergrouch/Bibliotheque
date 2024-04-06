package com.lange.biblioteque

import com.lange.biblioteque.plugins.configureGraphQL
import com.lange.biblioteque.plugins.configureHttp
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation

val client = HttpClient(CIO) {
    install(Logging)
    install(ClientContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
}

val server = embeddedServer(Netty, port = 80) {
    configureHttp()
    configureGraphQL()
}

fun main() {
    server.start(wait = true)
}