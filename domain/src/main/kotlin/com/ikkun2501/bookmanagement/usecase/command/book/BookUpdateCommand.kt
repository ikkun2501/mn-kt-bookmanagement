package com.ikkun2501.bookmanagement.usecase.command.book

import com.ikkun2501.bookmanagement.domain.Author
import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.SequenceId

/**
 * 書籍更新コマンド
 *
 * @property bookId
 * @property title
 * @property description
 * @property authorId
 */
data class BookUpdateCommand(
    val bookId: SequenceId<Book>,
    val title: String,
    val description: String,
    val authorId: SequenceId<Author>
)
