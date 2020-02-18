package com.ikkun2501.bookmanagement.domain

import java.time.LocalDate

data class Book(
    /**
     * ID
     */
    val bookId: String,
    /**
     * 書籍名
     */
    val title:String,
    /*
     * 著者ID
     */
    val authorId: String,
    /**
     * 概要
     */
    val summary: String
)

