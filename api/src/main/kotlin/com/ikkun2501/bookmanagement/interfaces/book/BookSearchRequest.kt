package com.ikkun2501.bookmanagement.interfaces.book

import io.micronaut.core.annotation.Introspected

@Introspected
data class BookSearchRequest(
    val keyword: String,
    val page: Int,
    val limit: Int
)
