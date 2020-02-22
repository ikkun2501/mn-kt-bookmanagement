package com.ikkun2501.bookmanagement.domain

data class Book(
    /**
     * ID
     */
    val bookId: Long,
    /**
     * 書籍名
     */
    val title: String,
    /*
     * 著者ID
     */
    val authorId: Long,
    /**
     * 説明
     */
    val description: String
)
