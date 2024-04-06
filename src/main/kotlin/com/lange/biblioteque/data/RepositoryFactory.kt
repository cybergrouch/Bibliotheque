package com.lange.biblioteque.data

import com.lange.biblioteque.data.openlibrary.OpenLibraryRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object RepositoryFactory {
    val openLibraryRepository: OpenLibraryRepository
        get() = OpenLibraryRepository(client = clientInstance)

    val clientInstance: HttpClient
        get() = HttpClient(CIO) {
            install(Logging)
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
}