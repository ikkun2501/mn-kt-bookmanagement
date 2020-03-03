package com.ikkun2501.bookmanagement.interfaces.book

import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.usecase.command.book.BookCommandService
import com.ikkun2501.bookmanagement.usecase.command.book.BookSaveCommand
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateCommand
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookQueryService
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchRequest
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

    override fun search(token: String, request: BookSearchRequest): List<BookSearchResultRow> {
        return bookQueryService.search(request)
    }

    override fun save(token: String, request: BookSaveRequest): BookDetail {

        val command = request.run {
            BookSaveCommand(
                title = title,
                authorId = SequenceId(authorId),
                description = description
            )
        }

        return bookCommandService.save(command).run {
            bookQueryService.detail(this.bookId.value)
        }
    }

    override fun update(token: String, request: BookUpdateRequest): BookDetail {

        val command = request.run {
            BookUpdateCommand(
                bookId = SequenceId(bookId),
                title = title,
                description = description,
                authorId = SequenceId(authorId)
            )
        }

        return bookCommandService.update(command).run {
            bookQueryService.detail(this.bookId.value)
        }
    }

    override fun delete(token: String, bookId: Int) {
        bookCommandService.delete(SequenceId(bookId))
    }
}
