package com.ikkun2501.bookmanagement.usecase.query.query

interface BookQuery {
    fun findById(bookId: String): BookSearchResultRow
    fun search(bookSearchParams: BookSearchParams): List<BookSearchResultRow>
}