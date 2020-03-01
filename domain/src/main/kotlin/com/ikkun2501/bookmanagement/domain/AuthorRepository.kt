package com.ikkun2501.bookmanagement.domain

/**
 * 書籍Repository
 *
 */
interface AuthorRepository {
    fun findById(authorId: SequenceId<Author>): Author?
    fun save(author: Author): Author
    fun update(author: Author): Author
    fun delete(authorId: SequenceId<Author>)
}
