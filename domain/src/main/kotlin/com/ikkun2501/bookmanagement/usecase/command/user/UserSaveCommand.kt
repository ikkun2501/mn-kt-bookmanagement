package com.ikkun2501.bookmanagement.usecase.command.user

import java.time.LocalDate

/**
 * ユーザ登録コマンド
 *
 * @property loginId
 * @property password
 * @property userName
 * @property birthday
 */
data class UserSaveCommand(
    val loginId: String,
    val password: String,
    val userName: String,
    val birthday: LocalDate
)
