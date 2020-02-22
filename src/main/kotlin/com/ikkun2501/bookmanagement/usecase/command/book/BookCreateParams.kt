package com.ikkun2501.bookmanagement.usecase.command.book

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class BookCreateParams(
    @field:NotBlank
    val title: String,
    val authorId: Long,
    @field:Size(max = 2000)
    val description: String
)
