package com.ikkun2501.bookmanagement.domain

/**
 * 著者Entitity
 *
 * @property authorId ID
 * @property authorName 著者名
 * @property description 説明
 */
data class Author(
    val authorId: Long,
    val authorName: String,
    val description: String
)
