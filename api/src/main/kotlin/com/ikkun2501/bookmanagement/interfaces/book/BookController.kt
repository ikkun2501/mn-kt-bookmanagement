package com.ikkun2501.bookmanagement.interfaces.book

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.usecase.command.book.BookCommandService
import com.ikkun2501.bookmanagement.usecase.command.book.BookSaveParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookQueryService
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import io.micronaut.http.annotation.Controller
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

/**
 * BookController
 *
 * @property bookCommandService 更新系のユースケース
 * @property bookQueryService 参照系のユースケース
 */
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/book")
class BookController(
    private val bookCommandService: BookCommandService,
    private val bookQueryService: BookQueryService
) : BookOperations {

    override fun show(token: String, bookId: Int): BookDetail {
        return bookQueryService.detail(bookId)
    }

    override fun search(token: String, bookSearchParams: BookSearchParams): List<BookSearchResultRow> {
        return bookQueryService.search(bookSearchParams)
    }

    override fun save(token: String, bookSaveParams: BookSaveParams): Book {
        return bookCommandService.save(bookSaveParams)
    }

    override fun update(token: String, book: BookUpdateParams): Book {
        return bookCommandService.update(book)
    }

    override fun delete(token: String, bookId: Int) {
        bookCommandService.delete(bookId)
    }
}
