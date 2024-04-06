package com.lange.biblioteque.data.openlibrary

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenLibraryBookCover(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
)

@Serializable
data class OpenLibraryBookClassifications(
    @SerialName("lc_classifications") val libraryOfCongress: List<String?>? = null,
    @SerialName("dewey_decimal_class") val deweyDecimal: List<String?>? = null
)

@Serializable
data class OpenLibraryBookIdentifiers(
    val goodreads: List<String?>? = null,
    val librarything: List<String?>? = null,
    @SerialName("isbn_10") val isbn10: List<String?>? = null,
    val lccn: List<String?>? = null,
    val oclc: List<String?>? = null,
    val openlibrary: List<String?>? = null
)

@Serializable
data class OpenLibraryBookNamedItem(
    val name: String? = null
)

@Serializable
data class OpenLibraryBookData(
    val title: String? = null,
    val subtitle: String? = null,
    val authors: List<OpenLibraryBookNamedItem?>? = null,
    val identifiers: OpenLibraryBookIdentifiers? = null,
    val classifications: OpenLibraryBookClassifications? = null,
    val publishers: List<OpenLibraryBookNamedItem?>? = null,
    @SerialName("publish_places") val publicationPlaces: List<OpenLibraryBookNamedItem?>? = null,
    @SerialName("publish_date") val publicationDate: String? = null,
    @SerialName("cover") val coverImages: OpenLibraryBookCover? = null,
    val subjects: List<OpenLibraryBookNamedItem?>? = null,
    @SerialName("subject_people") val subjectPeople: List<OpenLibraryBookNamedItem?>? = null,
    @SerialName("subject_times") val subjectTimes: List<OpenLibraryBookNamedItem?>? = null
)

@Serializable
data class OpenLibraryIsbnResponse(
    val records: Map<String, OpenLibraryBookRecord>? = null
)

@Serializable
data class OpenLibraryBookRecord(
    val data: OpenLibraryBookData? = null
)