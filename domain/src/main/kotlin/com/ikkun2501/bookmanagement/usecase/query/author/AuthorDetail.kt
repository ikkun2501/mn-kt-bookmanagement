package com.ikkun2501.bookmanagement.usecase.query.author

import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail

/**
 * 著者詳細
 *
 * @property authorId
 * @property authorName
 * @property authorDescription
 */
data class AuthorDetail(
    val authorId: Int,
    val authorName: String,
    val authorDescription: String,
    val books: List<BookDetail>
)
