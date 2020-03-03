package com.ikkun2501.bookmanagement.interfaces.user

import com.ikkun2501.bookmanagement.usecase.query.user.UserDetail
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.validation.Validated
import javax.validation.Valid

/**
 * UserApiインターフェイス
 *
 */
@Validated
interface UserOperations {

    /**
     * 登録
     *
     * @param userSaveRequest ユーザ登録パラメータ
     * @return ユーザ
     */
    @Post("/")
    fun save(@Valid @Body request: UserSaveRequest): UserDetail

    @Put("/detail")
    fun detailUpdate(@Header("Authorization") token: String, @Valid @Body request: UserDetailUpdateRequest): UserDetail
}
