package com.ikkun2501.bookmanagement.usecase.command.book

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.BookRepository
import com.ikkun2501.bookmanagement.domain.NotFoundException
import io.micronaut.spring.tx.annotation.Transactional
import javax.inject.Singleton

/**
 * 書籍の更新系ユースケース
 * @property bookRepository
 */
@Transactional
@Singleton
class BookCommand(val bookRepository: BookRepository) {

    fun create(createParams: BookCreateParams): Book {

        val book = createParams.run {
            Book(
                bookId = -1,
                description = description,
                authorId = authorId,
                title = title
            )
        }
        bookRepository.create(book)
        return bookRepository.create(book)
    }

    fun update(updateParams: BookUpdateParams): Book {
        val book = bookRepository.findById(updateParams.bookId) ?: throw NotFoundException()

        return updateParams.run {
            book.copy(
                bookId = bookId,
                title = title,
                authorId = authorId,
                description = description
            )
        }.run(bookRepository::update)
    }

    fun delete(bookId: Long) {
        bookRepository.delete(bookId)
    }
}
