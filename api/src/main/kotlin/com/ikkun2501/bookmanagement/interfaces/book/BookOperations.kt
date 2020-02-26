package com.ikkun2501.bookmanagement.interfaces.book

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.validation.Validated
import javax.validation.Valid

/**
 * BookAPI インターフェイス
 * MEMO サーバー側とクライアント側の両方で使用できるAPIのインターフェイス
 */
@Validated
interface BookOperations {

    /**
     * 詳細表示
     * @param bookId
     * @return
     */
    @Get("/{bookId}")
    fun show(@PathVariable bookId: Int): BookDetail

    /**
     * 検索
     *
     * @param bookSearchParams
     * @return
     */
    @Get("/search{?bookSearchParams*}")
    fun search(bookSearchParams: BookSearchParams): List<BookSearchResultRow>

    /**
     * 登録
     *
     * @param bookCreateParams
     * @return
     */
    @Post("/")
    fun create(@Valid @Body bookCreateParams: BookCreateParams): Book

    /**
     * 更新
     *
     * @param book
     * @return
     */
    @Put("/")
    fun update(@Valid @Body book: BookUpdateParams): Book

    /**
     * 削除
     *
     * @param bookId
     */
    @Delete("/{bookId}")
    fun delete(@PathVariable bookId: Int)
}
