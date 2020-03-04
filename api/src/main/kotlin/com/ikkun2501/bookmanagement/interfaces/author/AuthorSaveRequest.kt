package com.ikkun2501.bookmanagement.interfaces.author

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

/**
 * 著者登録パラメータ
 */
@Introspected
data class AuthorSaveRequest(
    @field:NotBlank
    val authorName: String,
    @field:NotBlank
    val description: String
)
