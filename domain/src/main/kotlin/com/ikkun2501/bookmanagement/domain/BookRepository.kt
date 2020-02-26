package com.ikkun2501.bookmanagement.domain

/**
 * 書籍Repository
 *
 */
interface BookRepository {
    fun findById(bookId: Int): Book?
    fun create(book: Book): Book
    fun update(book: Book): Book
    fun delete(bookId: Int)
}
