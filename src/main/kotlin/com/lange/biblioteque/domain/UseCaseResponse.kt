package com.lange.biblioteque.domain

sealed class UseCaseResponse<T>(
    open val data: T
) {
    data class Success<T>(
        override val data: T
    ) : UseCaseResponse<T>(data = data)

    data class Error<T>(
        override val data: T
    ) : UseCaseResponse<T>(data = data)
}