package com.ikkun2501.bookmanagement.usecase.command.author

import com.ikkun2501.bookmanagement.domain.Author
import com.ikkun2501.bookmanagement.domain.SequenceId

/**
 * 著者更新コマンド
 *
 * @property authorId
 * @property authorName
 * @property description
 */
data class AuthorUpdateCommand(
    val authorId: SequenceId<Author>,
    val authorName: String,
    val description: String
)
