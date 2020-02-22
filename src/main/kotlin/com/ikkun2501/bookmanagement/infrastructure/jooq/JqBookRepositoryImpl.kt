package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.example.db.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.BookRepository
import org.jooq.DSLContext
import javax.inject.Singleton

@Singleton
class JqBookRepositoryImpl(
    val dsl: DSLContext
) : BookRepository {

    override fun findById(bookId: Long): Book? {
        return dsl.fetchOne(BOOK, BOOK.BOOK_ID.eq(bookId)).toObject()
    }

    override fun create(book: Book): Book {
        val record = dsl.newRecord(BOOK)
            .values(null, book.authorId, book.title, book.description)
        record.store()
        return record.toObject()
    }

    override fun update(book: Book): Book {
        val record = dsl.fetchOne(BOOK, BOOK.BOOK_ID.eq(book.bookId))
            .values(book.bookId, book.authorId, book.title, book.description)
        record.store()
        return record.toObject()
    }

    override fun delete(bookId: Long) {
        dsl.deleteFrom(BOOK).where(BOOK.BOOK_ID.eq(bookId)).execute()
    }
}
