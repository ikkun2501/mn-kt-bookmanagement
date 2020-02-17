package com.ikkun2501.bookmanagement.infrastructure

import com.ikkun2501.bookmanagement.domain.Author
import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.NotFoundException
import com.ikkun2501.bookmanagement.infrastructure.memory.MemAuthorDataStore
import com.ikkun2501.bookmanagement.infrastructure.memory.MemBookDataStore
import com.ikkun2501.bookmanagement.usecase.query.query.BookQuery
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchResultRow
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchParams
import javax.inject.Singleton

@Singleton
class MemBookQueryImpl(
    private val memBookDataStore: MemBookDataStore,
    private val memAuthorDataStore: MemAuthorDataStore
) : BookQuery {

    override fun findById(bookId: String): BookSearchResultRow {
        val book = memBookDataStore.get(bookId) ?: throw NotFoundException()
        val author = requireNotNull(memAuthorDataStore.get(book.authorId))
        return toQueryModel(book, author)
    }

    override fun search(bookSearchParams: BookSearchParams): List<BookSearchResultRow> {

        val books: List<Book> = memBookDataStore.search(
            bookSearchParams.keyword,
            memAuthorDataStore.search(bookSearchParams.keyword).map { it.authorId }
        )

        val authorMap = memAuthorDataStore.findByIds(books.map { it.authorId }.distinct()).associateBy { it.authorId }

        return books
            .sortedBy { it.title }
            // paging
            .drop(bookSearchParams.offset)
            .take(bookSearchParams.max)
            // 変換
            .map { toQueryModel(it, requireNotNull(authorMap[it.authorId])) }
    }

    private fun toQueryModel(book: Book, author: Author): BookSearchResultRow {
        return BookSearchResultRow(
            bookId = book.bookId,
            publishDate = book.publishDate,
            summary = book.summary,
            title = book.title,
            authorId = author.authorId,
            authorName = author.authorName
        )
    }
}
