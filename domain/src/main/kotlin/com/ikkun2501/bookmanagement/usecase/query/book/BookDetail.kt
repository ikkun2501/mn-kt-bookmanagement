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
    val bookId: Long,
    val title: String,
    val bookDescription: String,
    val authorId: Long,
    val authorName: String,
    val authorDescription: String
)
