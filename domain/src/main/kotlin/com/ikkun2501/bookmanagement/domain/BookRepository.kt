package com.ikkun2501.bookmanagement.domain

/**
 * 書籍Repository
 *
 */
interface BookRepository {
    fun findById(bookId: SequenceId<Book>): Book?
    fun save(book: Book): Book
    fun update(book: Book): Book
    fun delete(bookId: SequenceId<Book>)
}
