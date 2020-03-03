package com.ikkun2501.bookmanagement.interfaces.author

import com.ikkun2501.bookmanagement.usecase.query.author.AuthorDetail
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchRequest
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchResultRow
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.validation.Validated
import javax.validation.Valid

/**
 * AuthorAPI インターフェイス
 */
@Validated
interface AuthorOperations {
    /**
     * 保存処理
     *
     * @param request 著者保存リクエスト
     * @return 著者
     */
    @Post("/")
    fun save(@Header("Authorization") token: String, @Valid @Body request: AuthorSaveRequest): AuthorDetail

    /**
     * 更新処理
     *
     * @param request 著者更新リクエスト
     * @return 著者
     */
    @Put("/")
    fun update(@Header("Authorization") token: String, @Valid @Body request: AuthorUpdateRequest): AuthorDetail

    /**
     * 著者詳細表示
     *
     * @param authorId 著者ID
     * @return 著者詳細
     */
    @Get("/{authorId}")
    fun show(@Header("Authorization") token: String, @PathVariable authorId: Int): AuthorDetail

    /**
     * 著者検索　
     *
     * @param request 著者検索らパラメータ
     * @return 著者検索結果
     */
    @Get("/search{?request*}")
    fun search(@Header("Authorization") token: String, request: AuthorSearchRequest): List<AuthorSearchResultRow>

    /**
     * 著者削除
     *
     * @param authorId 著者ID
     */
    @Delete("/{authorId}")
    fun delete(@Header("Authorization") token: String, authorId: Int)
}
