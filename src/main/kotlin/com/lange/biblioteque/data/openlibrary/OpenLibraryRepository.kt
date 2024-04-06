package com.lange.biblioteque.data.openlibrary

import com.lange.biblioteque.data.RepositoryFactory.clientInstance
import com.lange.biblioteque.data.clientInstance
import com.lange.biblioteque.domain.book.BookDataResponse
import com.lange.biblioteque.domain.book.isbn.BookDataResponse
import com.lange.biblioteque.domain.book.isbn.toBookDataResponse
import com.lange.biblioteque.domain.book.toBookDataResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class OpenLibraryRepository(
    val client: HttpClient = clientInstance
) {
    /**
     * Fetches the [BookDataResponse] for a given ISBN
     *
     * @receiver client The HTTP client to make the request from
     * @param isbn The ISBN-10 (no dashes) of the book
     */
    suspend fun getBookDataViaIsbn(isbn: String): BookDataResponse? =
        "http://openlibrary.org/api/volumes/brief/isbn/$isbn.json".let { url ->
            client.get(url)
        }.let { response ->
            response.body() as? OpenLibraryIsbnResponse
        }?.toBookDataResponse(isbn = isbn)
}