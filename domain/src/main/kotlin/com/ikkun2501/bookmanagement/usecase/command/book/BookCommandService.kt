package com.ikkun2501.bookmanagement.usecase.command.book

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.BookRepository
import com.ikkun2501.bookmanagement.domain.NotFoundException
import com.ikkun2501.bookmanagement.domain.SequenceId
import io.micronaut.spring.tx.annotation.Transactional
import javax.inject.Singleton

/**
 * 書籍の更新系ユースケース
 * @property bookRepository
 */
@Transactional
@Singleton
class BookCommandService(val bookRepository: BookRepository) {

    fun save(saveParams: BookSaveParams): Book {
        val book = saveParams.run {
            Book(
                bookId = SequenceId.notAssigned(),
                description = description,
                authorId = SequenceId(authorId),
                title = title
            )
        }
        return bookRepository.save(book)
    }

    fun update(updateParams: BookUpdateParams): Book {
        val book = bookRepository.findById(updateParams.bookId) ?: throw NotFoundException()

        return updateParams.run {
            book.copy(
                bookId = SequenceId(bookId),
                title = title,
                authorId = SequenceId(authorId),
                description = description
            )
        }.run(bookRepository::update)
    }

    fun delete(bookId: Int) {
        bookRepository.delete(bookId)
    }
}
