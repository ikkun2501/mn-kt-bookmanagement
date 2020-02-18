package com.ikkun2501.bookmanagement.usecase.command.book

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class BookCreateParams(
    @field:NotBlank
    val title:String,
    val authorId: String,
    val summary: String
)