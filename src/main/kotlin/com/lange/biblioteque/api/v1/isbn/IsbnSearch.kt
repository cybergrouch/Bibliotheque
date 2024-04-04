package com.lange.biblioteque.api.v1.isbn

import com.lange.biblioteque.api.v1.isbn.openlibrary.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

/**
 * Fetches the [BookDataResponse] for a given ISBN
 *
 * @receiver client The HTTP client to make the request from
 * @param isbn The ISBN-10 (no dashes) of the book
 */
suspend fun HttpClient.getBookDataViaIsbn(isbn: String): BookDataResponse? =
    "http://openlibrary.org/api/volumes/brief/isbn/$isbn.json".let { url ->
        this.get(url)
    }.let { response ->
        response.body() as? OpenLibraryIsbnResponse
    }?.toBookDataResponse(isbn = isbn)


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

enum class BookIdentifier {
    ISBN, ISBN_10, LC, DD, OCLC, LCCN, OPENLIBRARY, LIBRARYTHING, GOODREADS,
}

enum class BookImageType {
    FRONT_COVER
}

enum class BookTitleDescriptorType {
    TITLE, SUBTITLE
}

fun OpenLibraryIsbnResponse.toBookDataResponse(isbn: String): BookDataResponse? =
    this.records?.entries?.firstOrNull()?.value?.data?.let { bookRecord ->
        BookDataResponse(
            titles = bookRecord.mapToTitles,
            authors = bookRecord.authors.mapToListOfNames,
            publishers = bookRecord.publishers.mapToListOfNames,
            publishPlaces = bookRecord.publicationPlaces.mapToListOfNames,
            publicationYear = bookRecord.publicationDate,
            identifiers = createIdentifiers(
                identifiers = bookRecord.identifiers,
                classifications = bookRecord.classifications,
                isbn = isbn
            ),
            bookImages = bookRecord.coverImages?.mapToBookImages,
            subjectTags = createSubjectTags(
                topics = bookRecord.subjects,
                people = bookRecord.subjectPeople,
                times = bookRecord.subjectTimes
            )
        )
    }

@Serializable
data class BookTitle(
    val titleType: BookTitleDescriptorType,
    val title: String
)

val OpenLibraryBookData.mapToTitles: List<BookTitle>
    get() =
        listOf(
            *(title?.let {
                BookTitle(
                    titleType = BookTitleDescriptorType.TITLE,
                    title = it
                )
            }?.let { arrayOf(it) } ?: arrayOf()),
            *(subtitle?.let {
                BookTitle(
                    titleType = BookTitleDescriptorType.SUBTITLE,
                    title = it
                )
            }?.let { arrayOf(it) } ?: arrayOf())
        )

val List<OpenLibraryBookNamedItem?>?.mapToListOfNames: List<String>
    get() =
        this?.mapNotNull {
            it?.name
        } ?: emptyList()

val OpenLibraryBookIdentifiers.mapToIdentifiers: List<BookIdentity>
    get() =
        listOf(
            *(this.goodreads?.firstOrNull()?.let {
                arrayOf(
                    BookIdentity(
                        identifierType = BookIdentifier.GOODREADS,
                        identifier = it
                    )
                )
            } ?: arrayOf()),
            *(this.isbn10?.firstOrNull()?.let {
                arrayOf(
                    BookIdentity(
                        identifierType = BookIdentifier.ISBN_10,
                        identifier = it
                    )
                )
            } ?: arrayOf()),
            *(this.lccn?.firstOrNull()?.let {
                arrayOf(
                    BookIdentity(
                        identifierType = BookIdentifier.LCCN,
                        identifier = it
                    )
                )
            } ?: arrayOf()),
            *(this.librarything?.firstOrNull()?.let {
                arrayOf(
                    BookIdentity(
                        identifierType = BookIdentifier.LIBRARYTHING,
                        identifier = it
                    )
                )
            } ?: arrayOf()),
            *(this.oclc?.firstOrNull()?.let {
                arrayOf(
                    BookIdentity(
                        identifierType = BookIdentifier.OCLC,
                        identifier = it
                    )
                )
            } ?: arrayOf()),
            *(this.openlibrary?.firstOrNull()?.let {
                arrayOf(
                    BookIdentity(
                        identifierType = BookIdentifier.OPENLIBRARY,
                        identifier = it
                    )
                )
            } ?: arrayOf())
        )


@Serializable
data class BookIdentity(
    val identifierType: BookIdentifier,
    val identifier: String
)

val OpenLibraryBookClassifications.mapToIdentifiers: List<BookIdentity>
    get() =
        listOf(
            *(this.libraryOfCongress?.firstOrNull()?.let {
                arrayOf(
                    BookIdentity(
                        identifierType = BookIdentifier.LC,
                        identifier = it
                    )
                )
            } ?: arrayOf()),
            *(this.deweyDecimal?.firstOrNull()?.let {
                arrayOf(
                    BookIdentity(
                        identifierType = BookIdentifier.DD,
                        identifier = it
                    )
                )
            } ?: arrayOf())
        )

fun createIdentifiers(
    identifiers: OpenLibraryBookIdentifiers?,
    classifications: OpenLibraryBookClassifications?,
    isbn: String? = null
): List<BookIdentity> =
    mutableListOf(
        *(isbn?.let {
            arrayOf(
                BookIdentity(
                    identifierType = BookIdentifier.ISBN,
                    identifier = it
                )
            )
        } ?: arrayOf())
    ).also { aggregate ->
        identifiers?.mapToIdentifiers?.let { aggregate.addAll(it) }
    }.also { aggregate ->
        classifications?.mapToIdentifiers?.let { aggregate.addAll(it) }
    }

@Serializable
data class BookImage(
    val imageType: BookImageType,
    val imageUrl: String
)

val OpenLibraryBookCover.mapToBookImages: List<BookImage>
    get() =
        listOf(
            *(this.large?.let {
                arrayOf(
                    BookImage(
                        imageType = BookImageType.FRONT_COVER,
                        imageUrl = it
                    )
                )
            } ?: arrayOf())
        )

fun createSubjectTags(
    topics: List<OpenLibraryBookNamedItem?>?,
    people: List<OpenLibraryBookNamedItem?>?,
    times: List<OpenLibraryBookNamedItem?>?
): List<String> =
    mutableListOf(topics).plus(people).plus(times).filterNotNull().flatMap {
        it.filterNotNull().mapNotNull { namedItem ->
            namedItem.name
        }
    }
