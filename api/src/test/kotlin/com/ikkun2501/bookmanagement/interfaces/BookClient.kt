package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.interfaces.book.BookOperations
import com.ikkun2501.bookmanagement.usecase.command.book.BookRegisterParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import io.micronaut.http.client.annotation.Client

/**
 * BookAPIのClientインターフェイス
 * MEMO このインターフェイスからコンパイル時に実装が生成される
 */
@Client("/book")
interface BookClient : BookOperations {

    override fun show(token: String, bookId: Int): BookDetail

    override fun search(token: String, bookSearchParams: BookSearchParams): List<BookSearchResultRow>

    override fun register(token: String, bookRegisterParams: BookRegisterParams): Book

    override fun update(token: String, book: BookUpdateParams): Book

    override fun delete(token: String, bookId: Int)
}
