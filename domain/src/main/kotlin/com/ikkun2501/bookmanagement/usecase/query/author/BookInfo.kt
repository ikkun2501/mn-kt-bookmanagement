package com.ikkun2501.bookmanagement.usecase.query.author

/**
 * 書籍詳細
 *
 * @property bookId 書籍ID
 * @property title title
 * @property bookDescription 書籍説明
 * @property authorId 著者ID
 */
data class BookInfo(
    val bookId: Int,
    val title: String,
    val bookDescription: String,
    val authorId: Int
)
