package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.User
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.BookRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserAuthenticationRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserAuthorizationRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserDetailRecord
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import org.jooq.Record

/**
 * BookRecordからBookに変換
 *
 * @return
 */
fun BookRecord.toObject(): Book {
    return Book(
        bookId = bookId,
        authorId = authorId,
        title = title,
        description = description
    )
}

/**
 * RecordからBookDetailへの変換
 */
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

/**
 * RecordからBookSearchResultRowへの変換
 *
 * @return
 */
fun Record.toSearchResult(): BookSearchResultRow {
    return BookSearchResultRow(
        bookId = this[Tables.BOOK.BOOK_ID],
        bookDescription = this[Tables.BOOK.DESCRIPTION],
        title = this[Tables.BOOK.TITLE],
        authorId = this[Tables.BOOK.AUTHOR_ID],
        authorName = this[Tables.AUTHOR.AUTHOR_NAME]
    )
}

fun toUser(
    detail: UserDetailRecord,
    authentication: UserAuthenticationRecord,
    authorizations: List<UserAuthorizationRecord>
): User {
    return User(
        userId = detail.userId,
        loginId = authentication.loginId,
        password = authentication.password,
        roles = authorizations.map { it.userRole },
        birthday = detail.birthday,
        userName = detail.userName
    )
}
