package com.ikkun2501.bookmanagement.usecase.query.book

/**
 * 書籍詳細
 *
 * @property bookId
 * @property title
 * @property bookDescription
 * @property authorId
 * @property authorName
 * @property authorDescription
 */
data class BookDetail(
    val bookId: Int,
    val title: String,
    val bookDescription: String,
    val authorId: Int,
    val authorName: String,
    val authorDescription: String
)
