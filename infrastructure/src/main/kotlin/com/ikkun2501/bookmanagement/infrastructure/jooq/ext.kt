package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.domain.Author
import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.EncodedPassword
import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.domain.User
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.AuthorRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.BookRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserAuthenticationRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserAuthorizationRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserDetailRecord
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorDetail
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchResultRow
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import com.ikkun2501.bookmanagement.usecase.query.user.UserDetail
import org.jooq.Record

/**
 * BookRecordからBookに変換
 *
 * @return
 */
fun BookRecord.toObject(): Book {
    return Book(
        bookId = SequenceId(bookId),
        authorId = SequenceId(authorId),
        title = title,
        description = description
    )
}

/**
 * AuthorRecordからAuthorに変換
 *
 * @return
 */
fun AuthorRecord.toObject(): Author {
    return Author(
        authorId = SequenceId(authorId),
        authorName = authorName,
        description = description
    )
}

/**
 * RecordからBookDetailへの変換
 */
fun Record.toBookDetail(): BookDetail {
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
 * RecordからBookDetailへの変換
 */
fun UserDetailRecord.toUserDetail(): UserDetail {
    return UserDetail(
        userId = userId,
        userName = userName,
        birthday = birthday
    )
}

/**
 * RecordからBookDetailへの変換
 */
fun toAuthorDetail(authorRecord: AuthorRecord, books: List<BookRecord>): AuthorDetail {
    return AuthorDetail(
        authorId = authorRecord.authorId,
        authorName = authorRecord.authorName,
        authorDescription = authorRecord.description,
        books = books.map {
            BookDetail(
                bookId = it.bookId,
                authorName = authorRecord.authorName,
                authorId = authorRecord.authorId,
                title = it.title,
                bookDescription = it.description,
                authorDescription = authorRecord.description
            )
        }
    )
}

/**
 * RecordからBookSearchResultRowへの変換
 *
 * @return
 */
fun Record.toBookSearchResult(): BookSearchResultRow {
    return BookSearchResultRow(
        bookId = this[Tables.BOOK.BOOK_ID],
        bookDescription = this[Tables.BOOK.DESCRIPTION],
        title = this[Tables.BOOK.TITLE],
        authorId = this[Tables.BOOK.AUTHOR_ID],
        authorName = this[Tables.AUTHOR.AUTHOR_NAME]
    )
}

/**
 * RecordからBookSearchResultRowへの変換
 *
 * @return
 */
fun Record.toAuthorSearchResult(): AuthorSearchResultRow {
    return AuthorSearchResultRow(
        authorId = this[Tables.AUTHOR.AUTHOR_ID],
        authorName = this[Tables.AUTHOR.AUTHOR_NAME],
        authorDescription = this[Tables.AUTHOR.AUTHOR_NAME]
    )
}

fun toUser(
    detail: UserDetailRecord,
    authentication: UserAuthenticationRecord,
    authorizations: List<UserAuthorizationRecord>
): User {
    return User(
        userId = SequenceId(detail.userId),
        loginId = authentication.loginId,
        password = EncodedPassword(authentication.password),
        roles = authorizations.map { it.userRole },
        birthday = detail.birthday,
        userName = detail.userName
    )
}
