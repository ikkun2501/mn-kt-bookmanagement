package com.ikkun2501.bookmanagement.domain

data class Book(
    /**
     * ID
     */
    val bookId: String,
    /**
     * 書籍名
     */
    val title: String,
    /*
     * 著者ID
     */
    val authorId: String,
    /**
     * 説明
     */
    val description: String
)
