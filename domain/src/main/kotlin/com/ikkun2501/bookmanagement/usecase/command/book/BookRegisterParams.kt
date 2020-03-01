package com.ikkun2501.bookmanagement.usecase.command.book

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * 書籍登録パラメータ
 *
 * @property title
 * @property authorId
 * @property description
 */
@Introspected
data class BookRegisterParams(
    @field:NotBlank
    val title: String,
    val authorId: Int,
    @field:Size(max = 2000)
    val description: String
)
