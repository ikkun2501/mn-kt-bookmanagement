package com.ikkun2501.bookmanagement.domain

data class Author(
    /**
     * ID
     */
    val authorId: Long,
    /**
     * 著者名
     */
    val authorName: String,
    /**
     * 説明
     */
    val description: String
)
