package com.ikkun2501.bookmanagement.interfaces.user

import io.micronaut.core.annotation.Introspected
import java.time.LocalDate
import javax.validation.constraints.NotBlank

/**
 * ユーザ詳細更新パラメータ
 *
 */
@Introspected
data class UserDetailUpdateRequest(
    val userId: Int,
    @field:NotBlank
    val userName: String,
    val birthday: LocalDate
)
