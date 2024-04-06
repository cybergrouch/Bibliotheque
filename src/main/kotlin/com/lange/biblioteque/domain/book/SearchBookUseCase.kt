package com.lange.biblioteque.domain.book

import com.lange.biblioteque.data.RepositoryFactory
import com.lange.biblioteque.data.openlibrary.OpenLibraryRepository
import com.lange.biblioteque.domain.UseCase
import com.lange.biblioteque.domain.UseCaseResponse

class SearchBookUseCase(
    private val openLibraryRepository: OpenLibraryRepository = RepositoryFactory.openLibraryRepository
) : UseCase<SearchBookUseCase.Request, BookDataResponse>() {
    override suspend fun execute(request: Request): UseCaseResponse<BookDataResponse> =
        request.isbn.let { isbn ->
            openLibraryRepository.getBookDataViaIsbn(isbn = isbn)?.toBookDataResponse(isbn = isbn)
        }?.let {
            UseCaseResponse.Success(data = it)
        } ?: UseCaseResponse.Error(data = BookDataResponse(errors = listOf(BookError(message = "No data received"))))

    data class Request(
        val isbn: String
    )
}