package com.lange.biblioteque

import com.lange.biblioteque.plugins.configureGraphQL
import com.lange.biblioteque.plugins.configureHttp
import io.ktor.server.engine.*
import io.ktor.server.netty.*

val server = embeddedServer(Netty, port = 80) {
    configureHttp()
    configureGraphQL()
}

fun main() {
    server.start(wait = true)
}