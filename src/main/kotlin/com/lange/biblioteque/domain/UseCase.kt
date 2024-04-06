package com.lange.biblioteque.domain

abstract class UseCase<I, O> {
    abstract suspend fun execute(request: I): UseCaseResponse<O>
}