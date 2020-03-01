package com.ikkun2501.bookmanagement.usecase.command.user

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * ユーザ登録パラメータ
 *
 * @property loginId
 * @property password
 * @property confirmPassword
 */
@Introspected
data class UserSaveParams(
    @field:NotBlank
    val loginId: String,
    @field:Size(min = 8)
    val password: String,
    @field:Size(min = 8)
    val confirmPassword: String,
    @field:NotBlank
    val userName: String,
    @field:NotNull
    val birthday: LocalDate
)
