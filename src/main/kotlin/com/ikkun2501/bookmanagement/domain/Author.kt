package com.ikkun2501.bookmanagement.domain

data class Author(
    /**
     * ID
     */
    val authorId: String,
    /**
     * 著者名
     */
    val authorName: String,
    /**
     * 著者紹介
     */
    val aboutTheAuthor: String
)