package com.ikkun2501.authormanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Keys
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.toAuthorDetail
import com.ikkun2501.bookmanagement.infrastructure.jooq.toAuthorSearchResult
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorDetail
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorQueryService
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchParams
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchResultRow
import org.jooq.DSLContext
import org.jooq.Record
import javax.inject.Singleton

/**
 * AuthorQueryのJooqによる実装
 *
 * @property dslContext
 */
@Singleton
class JqAuthorQueryServiceImpl(
    private val dslContext: DSLContext
) : AuthorQueryService {

    override fun detail(authorId: Int): AuthorDetail {
        return dslContext.selectFrom(AUTHOR)
            .where(AUTHOR.AUTHOR_ID.eq(AUTHOR.AUTHOR_ID)).fetchOne().run {
                val books = fetchChildren(Keys.BOOK_AUTHOR_AUTHOR_ID_FK)
                toAuthorDetail(this, books)
            }
    }

    override fun search(authorSearchParams: AuthorSearchParams): List<AuthorSearchResultRow> {
        return dslContext.select().from(AUTHOR)
            .where(
                AUTHOR.AUTHOR_NAME.contains(authorSearchParams.keyword)
            )
            .orderBy(AUTHOR.AUTHOR_NAME)
            .offset((authorSearchParams.page - 1) * authorSearchParams.limit)
            .limit(authorSearchParams.limit)
            .map(Record::toAuthorSearchResult)
    }
}
