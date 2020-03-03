package com.ikkun2501.bookmanagement.usecase.command.author

import io.micronaut.core.annotation.Introspected

/**
 * 著者登録コマンド
 */
@Introspected
data class AuthorSaveCommand(
    val authorName: String,
    val description: String
)
