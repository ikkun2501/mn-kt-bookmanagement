package com.ikkun2501.bookmanagement.usecase.query.author

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
    val books: List<BookInfo>
)
