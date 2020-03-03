package com.ikkun2501.bookmanagement.usecase.command.author

import com.ikkun2501.bookmanagement.domain.Author
import com.ikkun2501.bookmanagement.domain.AuthorRepository
import com.ikkun2501.bookmanagement.domain.SequenceId
import io.micronaut.spring.tx.annotation.Transactional
import javax.inject.Singleton

/**
 * 著者更新系ユースケース
 *
 */
@Transactional
@Singleton
class AuthorCommandService(private val authorRepository: AuthorRepository) {
    fun save(authorSaveCommand: AuthorSaveCommand): Author {
        return authorSaveCommand.run {
            Author(
                authorId = SequenceId.notAssigned(),
                authorName = authorName,
                description = description
            )
        }.run(authorRepository::save)
    }

    fun update(authorUpdateCommand: AuthorUpdateCommand): Author {
        return authorUpdateCommand.run {
            Author(
                authorId = authorId,
                authorName = authorName,
                description = description
            )
        }.run(authorRepository::update)
    }

    fun delete(authorId: SequenceId<Author>) {
        authorRepository.delete(authorId)
    }
}
