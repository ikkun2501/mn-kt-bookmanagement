package com.ikkun2501.bookmanagement.interfaces.book

import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchRequest
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
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
     *
     * @param bookId 書籍ID
     * @return
     */
    @Get("/{bookId}")
    fun show(@Header("Authorization") token: String, @PathVariable bookId: Int): BookDetail

    /**
     * 検索
     *
     * @param request 書籍検索リクエスト
     * @return
     */
    @Get("/search{?request*}")
    fun search(@Header("Authorization") token: String, request: BookSearchRequest): List<BookSearchResultRow>

    /**
     * 登録
     *
     * @param request 書籍登録リクエスト
     * @return
     */
    @Post("/")
    fun save(@Header("Authorization") token: String, @Valid @Body request: BookSaveRequest): BookDetail

    /**
     * 書籍更新
     *
     * @param request 書籍更新リクエスト
     * @return
     */
    @Put("/")
    fun update(@Header("Authorization") token: String, @Valid @Body request: BookUpdateRequest): BookDetail

    /**
     * 書籍削除
     *
     * @param bookId 書籍ID
     */
    @Delete("/{bookId}")
    fun delete(@Header("Authorization") token: String, @PathVariable bookId: Int)
}
