package com.ikkun2501.bookmanagement.interfaces

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.created
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.validation.Validated
import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.usecase.command.book.BookCommand
import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.query.BookQuery
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchResultRow
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchParams
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Validated
@Controller("/book")
class BookController(
    private val bookCommand: BookCommand,
    private val bookQuery: BookQuery
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Get("/{bookId}")
    fun show(@PathVariable bookId: String): HttpResponse<BookSearchResultRow> {
        return bookQuery.findById(bookId).let(::ok)
    }

    @Get("/search{?bookSearchParams*}")
    fun search(bookSearchParams: BookSearchParams): HttpResponse<List<BookSearchResultRow>> {
        logger.info(bookSearchParams.toString())
        return bookQuery.search(bookSearchParams).let(::created)
    }

    @Post("/")
    fun create(@Body bookCreateParams: BookCreateParams): HttpResponse<Book> {
        return bookCommand.create(bookCreateParams).let(::created)
    }

    @Put("/")
    fun update(@Body book: BookUpdateParams): HttpResponse<Book> {
        return bookCommand.update(book).let(::ok)
    }

    @Delete("/{bookId}")
    fun delete(@PathVariable bookId: String): HttpResponse<Void> {
        bookCommand.delete(bookId)
        return HttpResponse.noContent()
    }
}
