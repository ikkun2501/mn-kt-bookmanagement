package com.ikkun2501.bookmanagement.usecase.command.author

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

/**
 * 著者登録パラメータ
 */
@Introspected
data class AuthorSaveParams(
    @field:NotBlank
    val authorName: String,
    @field:NotBlank
    val description: String
)
