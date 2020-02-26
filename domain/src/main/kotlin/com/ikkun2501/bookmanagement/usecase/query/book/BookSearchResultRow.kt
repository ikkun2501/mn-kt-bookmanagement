package com.ikkun2501.bookmanagement.usecase.query.book

/**
 * 書籍検索結果行
 *
 * @property bookId
 * @property title
 * @property bookDescription
 * @property authorId
 * @property authorName
 */
data class BookSearchResultRow(
    val bookId: Int,
    val title: String,
    val bookDescription: String,
    val authorId: Int,
    val authorName: String
)
