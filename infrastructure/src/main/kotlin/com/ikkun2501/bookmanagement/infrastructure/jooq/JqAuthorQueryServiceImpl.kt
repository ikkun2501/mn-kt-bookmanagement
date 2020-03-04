package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Keys
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorDetail
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorQueryService
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchRequest
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
            .where(AUTHOR.AUTHOR_ID.eq(authorId)).fetchOne().run {
                val books = fetchChildren(Keys.BOOK_AUTHOR_AUTHOR_ID_FK)
                toAuthorDetail(this, books)
            }
    }

    override fun search(authorSearchRequest: AuthorSearchRequest): List<AuthorSearchResultRow> {
        return dslContext.select().from(AUTHOR)
            .where(
                AUTHOR.AUTHOR_NAME.contains(authorSearchRequest.keyword)
            )
            .orderBy(AUTHOR.AUTHOR_NAME)
            .offset((authorSearchRequest.page - 1) * authorSearchRequest.limit)
            .limit(authorSearchRequest.limit)
            .map(Record::toAuthorSearchResult)
    }
}
