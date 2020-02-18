package com.ikkun2501.bookmanagement.usecase.query.book

import java.time.LocalDate

data class BookSearchResultRow(
    val bookId: String,
    val title: String,
    val summary: String,
    val authorId: String,
    val authorName: String
)