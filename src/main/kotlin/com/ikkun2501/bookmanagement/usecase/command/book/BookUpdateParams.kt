package com.ikkun2501.bookmanagement.usecase.command.book

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import javax.validation.constraints.NotBlank

@Introspected
data class BookUpdateParams(
    val bookId: String,
    @field:NotBlank
    val title: String,
    val isbncode: String,
    val authorId: String
)