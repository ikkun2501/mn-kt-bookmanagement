package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.usecase.command.book.BookCommand
import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.query.BookQuery
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchParams
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchResultRow
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.validation.Validated
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * BookController
 *
 * @property bookCommand 更新系のユースケース
 * @property bookQuery 参照系のユースケース
 */
@Controller("/book")
class BookController(
    private val bookCommand: BookCommand,
    private val bookQuery: BookQuery
) : BookOperations {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun show(@PathVariable bookId: String): BookSearchResultRow {
        return bookQuery.findById(bookId)
    }

    override fun search(bookSearchParams: BookSearchParams): List<BookSearchResultRow> {
        logger.info(bookSearchParams.toString())
        return bookQuery.search(bookSearchParams)
    }

    override fun create(@Body bookCreateParams: BookCreateParams): Book {
        return bookCommand.create(bookCreateParams)
    }

    override fun update(@Body book: BookUpdateParams): Book {
        return bookCommand.update(book)
    }

    override fun delete(@PathVariable bookId: String) {
        bookCommand.delete(bookId)
    }
}
