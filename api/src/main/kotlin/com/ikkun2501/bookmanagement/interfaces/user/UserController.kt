package com.ikkun2501.bookmanagement.interfaces.user

import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.usecase.command.user.UserCommandService
import com.ikkun2501.bookmanagement.usecase.command.user.UserDetailUpdateCommand
import com.ikkun2501.bookmanagement.usecase.command.user.UserSaveCommand
import com.ikkun2501.bookmanagement.usecase.query.user.UserDetail
import com.ikkun2501.bookmanagement.usecase.query.user.UserQueryService
import io.micronaut.http.annotation.Controller
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

/**
 * UserApi
 *
 * @property userCommandService 更新系のユースケース
 */
@Controller("/users")
class UserController(
    private val userCommandService: UserCommandService,
    private val userQueryService: UserQueryService
) : UserOperations {

    @Secured(SecurityRule.IS_ANONYMOUS)
    override fun save(request: UserSaveRequest): UserDetail {

        if (request.password != request.confirmPassword) {
            TODO("passwordが一致していません")
        }

        val command = request.run {
            UserSaveCommand(
                loginId = loginId,
                birthday = birthday,
                password = password,
                userName = userName
            )
        }
        return userCommandService.save(command).run {
            userQueryService.detail(this.userId.value)
        }
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    override fun detailUpdate(token: String, request: UserDetailUpdateRequest): UserDetail {

        // TODO ログインユーザのUserIDとパラメーターのUserIDが等しいか確認する

        val command = request.run {
            UserDetailUpdateCommand(
                userId = SequenceId(userId),
                userName = userName,
                birthday = birthday
            )
        }
        return userCommandService.updateUserDetail(command).run {
            userQueryService.detail(this.userId.value)
        }
    }
}
