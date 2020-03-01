package com.ikkun2501.bookmanagement.usecase.query.author

/**
 * 著者検索結果行
 *
 * @property authorId
 * @property authorName
 * @property authorDescription
 */
data class AuthorSearchResultRow(
    val authorId: Int,
    val authorName: String,
    val authorDescription: String
)
