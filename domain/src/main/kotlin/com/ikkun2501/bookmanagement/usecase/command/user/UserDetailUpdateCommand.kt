package com.ikkun2501.bookmanagement.usecase.command.user

import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.domain.User
import java.time.LocalDate

/**
 * ユーザ詳細更新コマンド
 *
 */
data class UserDetailUpdateCommand(
    val userId: SequenceId<User>,
    val userName: String,
    val birthday: LocalDate
)
