package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.domain.User
import com.ikkun2501.bookmanagement.domain.UserRepository
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.USER_AUTHENTICATION
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.USER_AUTHORIZATION
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.USER_DETAIL
import org.jooq.DSLContext
import javax.inject.Singleton

/**
 * BookRepositoryのJooqによる実装
 *
 * @property dsl
 */
@Singleton
class JqUserRepositoryImpl(
    private val dsl: DSLContext
) : UserRepository {

    override fun findByLoginId(loginId: String): User? {
        val userAuthentication =
            dsl.fetchOne(USER_AUTHENTICATION, USER_AUTHENTICATION.LOGIN_ID.eq(loginId)) ?: return null

        return findByUserId(userAuthentication.userId)
    }

    override fun findByUserId(userId: Int): User? {
        val detail = dsl.fetchOne(USER_DETAIL, USER_DETAIL.USER_ID.eq(userId)) ?: return null
        val authorization = dsl.fetch(USER_AUTHORIZATION, USER_AUTHORIZATION.USER_ID.eq(userId)).toList()
        val authentication = dsl.fetchOne(USER_AUTHENTICATION, USER_AUTHENTICATION.USER_ID.eq(userId)) ?: return null

        return User(
            userId = SequenceId(detail.userId),
            userName = detail.userName,
            birthday = detail.birthday,
            roles = authorization.map { it.userRole },
            loginId = authentication.loginId,
            password = authentication.password
        )
    }
}
