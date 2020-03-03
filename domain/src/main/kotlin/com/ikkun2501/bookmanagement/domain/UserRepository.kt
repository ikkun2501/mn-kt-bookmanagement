package com.ikkun2501.bookmanagement.domain

interface UserRepository {
    fun findByLoginId(loginId: String): User?
    fun findByUserId(userId: SequenceId<User>): User?
    fun save(user: User): User
    fun update(user: User): User
}
