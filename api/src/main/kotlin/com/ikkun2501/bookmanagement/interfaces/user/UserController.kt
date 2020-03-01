package com.ikkun2501.bookmanagement.interfaces.user

import com.ikkun2501.bookmanagement.domain.User
import com.ikkun2501.bookmanagement.usecase.command.user.UserCommandService
import com.ikkun2501.bookmanagement.usecase.command.user.UserDetailUpdateParams
import com.ikkun2501.bookmanagement.usecase.command.user.UserSaveParams
import io.micronaut.http.annotation.Controller
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

/**
 * UserApi
 *
 * @property userCommandService 更新系のユースケース
 */
@Controller("/users")
class UserController(private val userCommandService: UserCommandService) : UserOperations {

    @Secured(SecurityRule.IS_ANONYMOUS)
    override fun save(userSaveParams: UserSaveParams): User {

        if (userSaveParams.password != userSaveParams.confirmPassword) {
            TODO("passwordが一致していません")
        }

        return userCommandService.save(userSaveParams)
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    override fun detailUpdate(token: String, userDetailUpdateParams: UserDetailUpdateParams): User {

        // TODO ログインユーザのUserIDとパラメーターのUserIDが等しいか確認する
        // ThreadLocalかリクエストスコープのBeanでログインしているユーザを表現したい

        return userCommandService.updateUserDetail(userDetailUpdateParams)
    }
}
