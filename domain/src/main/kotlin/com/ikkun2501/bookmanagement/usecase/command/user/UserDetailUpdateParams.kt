package com.ikkun2501.bookmanagement.usecase.command.user

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import javax.validation.constraints.NotBlank

/**
 * ユーザ詳細更新パラメータ
 *
 */
@Introspected
data class UserDetailUpdateParams(
    val userId: Int,
    @field:NotBlank
    val userName: String,
    val birthday: LocalDate
)
