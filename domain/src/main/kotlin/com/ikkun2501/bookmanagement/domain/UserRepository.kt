package com.ikkun2501.bookmanagement.domain

interface UserRepository {
    fun findByLoginId(loginId: String): User?
    fun findByUserId(userId: Int): User?
}
