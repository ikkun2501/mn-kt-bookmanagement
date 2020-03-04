package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.domain.NotFoundException
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookQueryService
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchRequest
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import org.jooq.DSLContext
import org.jooq.Record
import javax.inject.Singleton

/**
 * BookQueryのJooqによる実装
 *
 * @property dslContext
 */
@Singleton
class JqBookQueryServiceImpl(
    private val dslContext: DSLContext
) : BookQueryService {

    override fun detail(bookId: Int): BookDetail {
        return dslContext.select().from(BOOK.innerJoin(AUTHOR).onKey(BOOK.AUTHOR_ID))
            .where(BOOK.AUTHOR_ID.eq(AUTHOR.AUTHOR_ID)).fetchOne()
            ?.let(Record::toBookDetail)
            ?: throw NotFoundException()
    }

    override fun search(bookSearchRequest: BookSearchRequest): List<BookSearchResultRow> {
        return dslContext.select().from(BOOK.innerJoin(AUTHOR).onKey(BOOK.AUTHOR_ID))
            .where(
                BOOK.TITLE.contains(bookSearchRequest.keyword)
                    .or(AUTHOR.AUTHOR_NAME.contains(bookSearchRequest.keyword))
            )
            .orderBy(BOOK.TITLE)
            .offset((bookSearchRequest.page - 1) * bookSearchRequest.limit)
            .limit(bookSearchRequest.limit)
            .map(Record::toBookSearchResult)
    }
}
