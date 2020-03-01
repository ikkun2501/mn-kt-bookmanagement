package com.ikkun2501.bookmanagement.interfaces.user

import com.ikkun2501.bookmanagement.domain.User
import com.ikkun2501.bookmanagement.usecase.command.user.UserDetailUpdateParams
import com.ikkun2501.bookmanagement.usecase.command.user.UserRegisterParams
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
     * @param userRegisterParams ユーザ登録パラメータ
     * @return ユーザ
     */
    @Post("/")
    fun register(@Valid @Body userRegisterParams: UserRegisterParams): User

    @Put("/detail")
    fun detailUpdate(@Header("Authorization") token: String, @Valid @Body userDetailUpdateParams: UserDetailUpdateParams): User
}
