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

    fun save(saveCommand: BookSaveCommand): Book {
        val book = saveCommand.run {
            Book(
                bookId = SequenceId.notAssigned(),
                description = description,
                authorId = authorId,
                title = title
            )
        }
        return bookRepository.save(book)
    }

    fun update(updateCommand: BookUpdateCommand): Book {
        val book = bookRepository.findById(updateCommand.bookId) ?: throw NotFoundException()

        return updateCommand.run {
            book.copy(
                bookId = bookId,
                title = title,
                authorId = authorId,
                description = description
            )
        }.run(bookRepository::update)
    }

    fun delete(bookId: SequenceId<Book>) {
        bookRepository.delete(bookId)
    }
}
