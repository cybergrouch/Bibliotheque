package com.lange.biblioteque.plugins

import com.apurebase.kgraphql.GraphQL
import com.lange.biblioteque.api.v1.isbn.bookSchema
import io.ktor.server.application.*

fun Application.configureGraphQL() {
    install(GraphQL) {
        playground = true
        schema {
            bookSchema()
        }
    }
}