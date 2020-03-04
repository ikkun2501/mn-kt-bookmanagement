package com.ikkun2501.bookmanagement.usecase.query.user

import java.time.LocalDate

data class UserDetail(
    val userId: Int,
    val userName: String,
    val birthday: LocalDate
)
