package com.lange.biblioteque.domain.book

import kotlinx.serialization.Serializable

@Serializable
data class BookDataResponse(
    val titles: List<BookTitle>,
    val authors: List<String>?,
    val publishers: List<String>?,
    val publishPlaces: List<String>?,
    val publicationYear: String?,
    val identifiers: List<BookIdentity>?,
    val bookImages: List<BookImage>?,
    val subjectTags: List<String>
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

enum class BookIdentifier {
    ISBN, ISBN_10, LC, DD, OCLC, LCCN, OPENLIBRARY, LIBRARYTHING, GOODREADS,
}

enum class BookImageType {
    FRONT_COVER
}

enum class BookTitleDescriptorType {
    TITLE, SUBTITLE
}