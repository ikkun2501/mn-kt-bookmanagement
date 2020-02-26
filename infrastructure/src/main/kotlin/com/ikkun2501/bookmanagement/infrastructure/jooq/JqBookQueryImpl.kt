package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookQuery
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchParams
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
class JqBookQueryImpl(
    private val dslContext: DSLContext
) : BookQuery {

    override fun detail(bookId: Int): BookDetail {
        return dslContext.select().from(BOOK.innerJoin(AUTHOR).onKey(BOOK.AUTHOR_ID))
            .where(BOOK.AUTHOR_ID.eq(AUTHOR.AUTHOR_ID)).fetchOne()
            .let(Record::toDetail)
    }

    override fun search(bookSearchParams: BookSearchParams): List<BookSearchResultRow> {
        return dslContext.select().from(BOOK.innerJoin(AUTHOR).onKey(BOOK.AUTHOR_ID))
            .where(
                BOOK.TITLE.contains(bookSearchParams.keyword)
                    .or(AUTHOR.AUTHOR_NAME.contains(bookSearchParams.keyword))
            )
            .orderBy(BOOK.TITLE)
            .offset((bookSearchParams.page - 1) * bookSearchParams.limit)
            .limit(bookSearchParams.limit)
            .map(Record::toSearchResult)
    }
}
