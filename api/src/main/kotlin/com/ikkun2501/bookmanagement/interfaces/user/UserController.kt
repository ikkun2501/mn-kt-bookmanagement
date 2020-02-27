package com.ikkun2501.bookmanagement.interfaces.user

import com.ikkun2501.bookmanagement.domain.User
import com.ikkun2501.bookmanagement.usecase.command.user.UserCommand
import com.ikkun2501.bookmanagement.usecase.command.user.UserDetailUpdateParams
import com.ikkun2501.bookmanagement.usecase.command.user.UserRegisterParams
import io.micronaut.http.annotation.Controller
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

/**
 * UserApi
 *
 * @property userCommand 更新系のユースケース
 */
@Controller("/users")
class UserController(private val userCommand: UserCommand) : UserOperations {

    @Secured(SecurityRule.IS_ANONYMOUS)
    override fun register(userRegisterParams: UserRegisterParams): User {

        if (userRegisterParams.password != userRegisterParams.confirmPassword) {
            TODO("passwordが一致していません")
        }

        return userCommand.register(userRegisterParams)
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    override fun detailUpdate(token: String, userDetailUpdateParams: UserDetailUpdateParams): User {

        // TODO ログインユーザのUserIDとパラメーターのUserIDが等しいか確認する
        // ThreadLocalかリクエストスコープのBeanでログインしているユーザを表現したい

        return userCommand.updateUserDetail(userDetailUpdateParams)
    }
}
