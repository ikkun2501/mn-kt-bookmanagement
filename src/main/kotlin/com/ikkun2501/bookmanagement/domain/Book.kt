package com.ikkun2501.bookmanagement.domain

/**
 * 書籍Entity
 *
 * @property bookId ID
 * @property title 書籍名
 * @property authorId 著者ID
 * @property description 説明
 */
data class Book(
    val bookId: Long,
    val title: String,
    val authorId: Long,
    val description: String
)
