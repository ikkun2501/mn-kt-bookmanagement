package com.ikkun2501.bookmanagement.usecase.command.book

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * 書籍更新パラメータ
 *
 * @property bookId
 * @property title
 * @property description
 * @property authorId
 */
@Introspected
data class BookUpdateParams(
    val bookId: Long,
    @field:NotBlank
    val title: String,
    @field:Size(max = 2000)
    val description: String,
    val authorId: Long
)
