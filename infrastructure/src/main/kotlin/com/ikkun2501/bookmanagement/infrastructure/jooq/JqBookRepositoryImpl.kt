package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.BookRepository
import org.jooq.DSLContext
import javax.inject.Singleton

/**
 * BookRepositoryのJooqによる実装
 *
 * @property dsl
 */
@Singleton
class JqBookRepositoryImpl(
    val dsl: DSLContext
) : BookRepository {

    override fun findById(bookId: Int): Book? {
        return dsl.fetchOne(BOOK, BOOK.BOOK_ID.eq(bookId)).toObject()
    }

    override fun register(book: Book): Book {
        val record = dsl.newRecord(BOOK)
            .values(null, book.authorId.value, book.title, book.description)
        record.store()
        return record.toObject()
    }

    override fun update(book: Book): Book {
        val record = dsl.fetchOne(BOOK, BOOK.BOOK_ID.eq(book.bookId.value))
            .values(book.bookId.value, book.authorId.value, book.title, book.description)
        record.store()
        return record.toObject()
    }

    override fun delete(bookId: Int) {
        dsl.deleteFrom(BOOK).where(BOOK.BOOK_ID.eq(bookId)).execute()
    }
}
