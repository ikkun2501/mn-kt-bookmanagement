package com.ikkun2501.bookmanagement.usecase.query.book

interface BookQuery {
    fun detail(bookId: Long): BookDetail
    fun search(bookSearchParams: BookSearchParams): List<BookSearchResultRow>
}
