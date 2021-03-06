package com.ikkun2501.bookmanagement.interfaces.book

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
data class BookUpdateRequest(
    val bookId: Int,
    @field:NotBlank
    val title: String,
    @field:Size(max = 2000)
    val description: String,
    val authorId: Int
)
