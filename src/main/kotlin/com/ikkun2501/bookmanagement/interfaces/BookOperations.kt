package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import io.micronaut.validation.Validated
import javax.validation.Valid

/**
 * BookAPI インターフェイス
 * MEMO サーバー側とクライアント側の両方で使用できるAPIのインターフェイス
 */
@Validated
interface BookOperations {

    @Get("/{bookId}")
    fun show(@PathVariable bookId: String): BookSearchResultRow

    @Get("/search{?bookSearchParams*}")
    fun search(bookSearchParams: BookSearchParams): List<BookSearchResultRow>

    @Post("/")
    fun create(@Valid @Body bookCreateParams: BookCreateParams): Book

    @Put("/")
    fun update(@Valid @Body book: BookUpdateParams): Book

    @Delete("/{bookId}")
    fun delete(@PathVariable bookId: String)
}
