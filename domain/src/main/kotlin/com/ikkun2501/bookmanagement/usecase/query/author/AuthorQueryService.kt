package com.ikkun2501.bookmanagement.usecase.query.author

/**
 * 著者参照系ユースケースのインターフェイス
 */
interface AuthorQueryService {
    fun detail(authorId: Int): AuthorDetail
    fun search(authorSearchParams: AuthorSearchParams): List<AuthorSearchResultRow>
}
