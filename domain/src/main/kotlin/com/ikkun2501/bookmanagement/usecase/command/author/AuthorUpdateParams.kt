package com.ikkun2501.bookmanagement.usecase.command.author

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

/**
 * 著者更新パラメータ
 *
 * @property authorId
 * @property authorName
 * @property description
 */
@Introspected
data class AuthorUpdateParams(
    val authorId: Int,
    @field:NotBlank
    val authorName: String,
    @field:NotBlank
    val description: String
)
