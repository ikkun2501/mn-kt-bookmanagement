package com.ikkun2501.bookmanagement.usecase.query.book

data class BookDetail(
    val bookId: Long,
    val title: String,
    val bookDescription: String,
    val authorId: Long,
    val authorName: String,
    val authorDescription: String
)
