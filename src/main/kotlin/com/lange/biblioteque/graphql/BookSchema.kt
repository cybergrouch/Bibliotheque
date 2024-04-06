package com.lange.biblioteque.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.lange.biblioteque.data.RepositoryFactory
import com.lange.biblioteque.data.openlibrary.OpenLibraryRepository
import com.lange.biblioteque.domain.book.*

fun SchemaBuilder.bookSchema(
    openLibraryRepository: OpenLibraryRepository = RepositoryFactory.openLibraryRepository
) {
    type<BookDataResponse> {
        description = "Information about a book"
    }

    type<BookTitle> {
        description = "Information about a book's title"
    }

    type<BookIdentity> {
        description = "Information about a book's identity"
    }

    type<BookImage> {
        description = "Information regarding an image of the book"
    }

    enum<BookImageType> {
        description = "Descriptor for one of the book's image"
    }

    enum<BookTitleDescriptorType> {
        description = "Descriptor for one of the book's title"
    }

    enum<BookIdentifier> {
        description = "Descriptor for one of the book's identifier"
    }

    query("book") {
        description = "Returns information about a book"
        resolver { isbn: String ->
            openLibraryRepository.getBookDataViaIsbn(isbn = isbn)
        }
    }
}