package com.ikkun2501.bookmanagement.usecase.command.book

import java.time.LocalDate

data class BookUpdateParams(
    val bookId: String,
    val title: String,
    val authorId: String,
    val summary: String,
    val publishDate: LocalDate
)