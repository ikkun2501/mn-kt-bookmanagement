package com.ikkun2501.bookmanagement.interfaces.book

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.usecase.command.book.BookCommand
import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookQuery
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import io.micronaut.http.annotation.Controller
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

/**
 * BookController
 *
 * @property bookCommand 更新系のユースケース
 * @property bookQuery 参照系のユースケース
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/book")
class BookController(
    private val bookCommand: BookCommand,
    private val bookQuery: BookQuery
) : BookOperations {

    override fun show(token: String, bookId: Int): BookDetail {
        return bookQuery.detail(bookId)
    }

    override fun search(token: String, bookSearchParams: BookSearchParams): List<BookSearchResultRow> {
        return bookQuery.search(bookSearchParams)
    }

    override fun create(token: String, bookCreateParams: BookCreateParams): Book {
        return bookCommand.create(bookCreateParams)
    }

    override fun update(token: String, book: BookUpdateParams): Book {
        return bookCommand.update(book)
    }

    override fun delete(token: String, bookId: Int) {
        bookCommand.delete(bookId)
    }
}
