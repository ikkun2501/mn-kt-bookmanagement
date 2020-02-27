package com.ikkun2501.bookmanagement.domain

import java.time.LocalDate

data class User(
    val userId: SequenceId<User>,
    val loginId: String,
    val password: String,
    val roles: List<String>,
    val userName: String,
    val birthday: LocalDate
)
