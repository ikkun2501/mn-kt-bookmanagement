package com.ikkun2501.bookmanagement.usecase.query.user

interface UserQueryService {
    fun detail(userId: Int): UserDetail
}
