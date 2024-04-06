package com.lange.biblioteque.domain.book

import kotlinx.serialization.Serializable

@Serializable
data class BookDataResponse(
    val titles: List<BookTitle>? = null,
    val authors: List<String>? = null,
    val publishers: List<String>? = null,
    val publishPlaces: List<String>? = null,
    val publicationYear: String? = null,
    val identifiers: List<BookIdentity>? = null,
    val bookImages: List<BookImage>? = null,
    val subjectTags: List<String>? = null,
    val errors: List<BookError>? = null
)

@Serializable
data class BookTitle(
    val titleType: BookTitleDescriptorType,
    val title: String
)

@Serializable
data class BookIdentity(
    val identifierType: BookIdentifier,
    val identifier: String
)

@Serializable
data class BookImage(
    val imageType: BookImageType,
    val imageUrl: String
)

@Serializable
data class BookError(
    val message: String
)

enum class BookIdentifier {
    ISBN, ISBN_10, LC, DD, OCLC, LCCN, OPENLIBRARY, LIBRARYTHING, GOODREADS,
}

enum class BookImageType {
    FRONT_COVER
}

enum class BookTitleDescriptorType {
    TITLE, SUBTITLE
}