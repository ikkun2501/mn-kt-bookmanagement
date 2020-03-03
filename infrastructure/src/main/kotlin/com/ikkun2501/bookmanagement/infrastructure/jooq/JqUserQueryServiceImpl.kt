package com.ikkun2501.bookmanagement.infrastructure.jooq

import com.ikkun2501.bookmanagement.domain.NotFoundException
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.USER_DETAIL
import com.ikkun2501.bookmanagement.usecase.query.user.UserDetail
import com.ikkun2501.bookmanagement.usecase.query.user.UserQueryService
import org.jooq.DSLContext
import javax.inject.Singleton

/**
 * BookRepositoryのJooqによる実装
 *
 * @property dsl
 */
@Singleton
class JqUserQueryServiceImpl(
    private val dsl: DSLContext
) : UserQueryService {
    override fun detail(userId: Int): UserDetail {
        return dsl.fetchOne(USER_DETAIL, USER_DETAIL.USER_ID.eq(userId))
            ?.toUserDetail()
            ?: throw NotFoundException()
    }
}
