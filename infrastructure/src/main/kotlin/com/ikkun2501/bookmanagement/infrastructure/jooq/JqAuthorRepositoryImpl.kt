package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.domain.Author
import com.ikkun2501.bookmanagement.domain.AuthorRepository
import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import org.jooq.DSLContext
import javax.inject.Singleton

/**
 * AuthorRepositoryのJooqによる実装
 *
 * @property dsl
 */
@Singleton
class JqAuthorRepositoryImpl(
    val dsl: DSLContext
) : AuthorRepository {

    override fun findById(authorId: SequenceId<Author>): Author? {
        return dsl.fetchOne(AUTHOR, AUTHOR.AUTHOR_ID.eq(authorId.value)).toObject()
    }

    override fun save(author: Author): Author {
        val record = dsl.newRecord(AUTHOR)
            .values(null, author.authorName, author.description)
        record.store()
        return record.toObject()
    }

    override fun update(author: Author): Author {
        val record = dsl.fetchOne(AUTHOR, AUTHOR.AUTHOR_ID.eq(author.authorId.value))
            .values(author.authorId.value, author.authorName, author.description)
        record.store()
        return record.toObject()
    }

    override fun delete(authorId: SequenceId<Author>) {
        dsl.deleteFrom(AUTHOR).where(AUTHOR.AUTHOR_ID.eq(authorId.value)).execute()
    }
}
