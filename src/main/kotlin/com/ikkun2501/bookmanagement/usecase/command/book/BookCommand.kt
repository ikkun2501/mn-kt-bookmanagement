package com.ikkun2501.bookmanagement.usecase.command.book

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.BookRepository
import com.ikkun2501.bookmanagement.domain.NotFoundException
import java.util.UUID
import javax.inject.Singleton

@Singleton
class BookCommand(val bookRepository: BookRepository) {

    fun create(createParams: BookCreateParams): Book {
        val book = createParams.run {
            Book(
                bookId = UUID.randomUUID().toString(),
                summary = summary,
                authorId = authorId,
                title = title
            )
        }
        return bookRepository.create(book)
    }

    fun update(updateParams: BookUpdateParams): Book {
        val book = bookRepository.findById(updateParams.bookId) ?: throw NotFoundException()

        return updateParams.run {
            book.copy(
                bookId = bookId,
                title = title,
                authorId = authorId
            )
        }.run(bookRepository::update)
    }

    fun delete(bookId: String) {
        bookRepository.delete(bookId)
    }
}
