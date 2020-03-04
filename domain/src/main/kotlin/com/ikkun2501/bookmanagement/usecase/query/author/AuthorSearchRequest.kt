package com.ikkun2501.bookmanagement.usecase.query.author

import io.micronaut.core.annotation.Introspected

/**
 * 著者検索パラメータ
 *
 * @property keyword
 * @property page
 * @property limit
 */
@Introspected
data class AuthorSearchRequest(
    val keyword: String,
    val page: Int,
    val limit: Int
)
