package com.ikkun2501.bookmanagement.domain

/**
 * 書籍Repository
 *
 */
interface BookRepository {
    fun findById(bookId: Long): Book?
    fun create(book: Book): Book
    fun update(book: Book): Book
    fun delete(bookId: Long)
}
