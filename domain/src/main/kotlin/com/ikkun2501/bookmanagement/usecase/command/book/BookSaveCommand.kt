package com.ikkun2501.bookmanagement.usecase.command.book

import com.ikkun2501.bookmanagement.domain.Author
import com.ikkun2501.bookmanagement.domain.SequenceId

/**
 * 書籍登録コマンド
 *
 * @property title
 * @property authorId
 * @property description
 */
data class BookSaveCommand(
    val title: String,
    val authorId: SequenceId<Author>,
    val description: String
)
