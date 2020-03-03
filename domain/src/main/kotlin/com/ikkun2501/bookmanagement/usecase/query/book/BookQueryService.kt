package com.ikkun2501.bookmanagement.usecase.query.book

/**
 * 書籍参照系ユースケースのインターフェイス
 */
interface BookQueryService {
    fun detail(bookId: Int): BookDetail
    fun search(bookSearchRequest: BookSearchRequest): List<BookSearchResultRow>
}
