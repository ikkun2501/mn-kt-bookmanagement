package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.example.db.jooq.gen.Tables
import com.example.db.jooq.gen.tables.records.BookRecord
import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import org.jooq.Record

fun BookRecord.toObject(): Book {
    return Book(
        bookId = bookId,
        authorId = authorId,
        title = title,
        description = description
    )
}

fun Record.toDetail(): BookDetail {
    return BookDetail(
        bookId = this[Tables.BOOK.BOOK_ID],
        bookDescription = this[Tables.BOOK.DESCRIPTION],
        title = this[Tables.BOOK.TITLE],
        authorId = this[Tables.BOOK.AUTHOR_ID],
        authorName = this[Tables.AUTHOR.AUTHOR_NAME],
        authorDescription = this[Tables.AUTHOR.DESCRIPTION]
    )
}

fun Record.toSearchResult(): BookSearchResultRow {
    return BookSearchResultRow(
        bookId = this[Tables.BOOK.BOOK_ID],
        bookDescription = this[Tables.BOOK.DESCRIPTION],
        title = this[Tables.BOOK.TITLE],
        authorId = this[Tables.BOOK.AUTHOR_ID],
        authorName = this[Tables.AUTHOR.AUTHOR_NAME]
    )
}
