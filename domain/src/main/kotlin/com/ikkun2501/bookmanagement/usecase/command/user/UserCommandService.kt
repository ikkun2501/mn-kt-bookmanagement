package com.ikkun2501.bookmanagement.usecase.command.user

import com.ikkun2501.bookmanagement.domain.DuplicateException
import com.ikkun2501.bookmanagement.domain.NotFoundException
import com.ikkun2501.bookmanagement.domain.RawPassword
import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.domain.User
import com.ikkun2501.bookmanagement.domain.UserRepository
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import io.micronaut.spring.tx.annotation.Transactional
import javax.inject.Singleton

@Transactional
@Singleton
class UserCommandService(val userRepository: UserRepository) {

    /**
     * ユーザ認証
     *
     * @param request
     * @return
     */
    fun auth(request: AuthenticationRequest<*, *>): AuthenticationResponse {
        val user = userRepository.findByLoginId(request.identity as String) ?: return AuthenticationFailed()

        if (!user.password.matches(request.secret as String)) {
            return AuthenticationFailed()
        }

        return UserDetails(
            user.userName,
            user.roles
        )
    }

    /**
     * ユーザ登録
     *
     * @param userSaveCommand
     * @return
     */
    fun save(userSaveCommand: UserSaveCommand): User {

        // ログインIDが既に登録されていたら
        if (userRepository.findByLoginId(userSaveCommand.loginId) != null) {
            throw DuplicateException()
        }
        val user = userSaveCommand.run {
            User(
                userId = SequenceId.notAssigned(),
                loginId = loginId,
                password = RawPassword(password).encode(),
                roles = listOf("ROLE_USER"),
                userName = userName,
                birthday = birthday
            )
        }

        return userRepository.save(user)
    }

    /**
     * ユーザ詳細更新
     *
     * @param userDetailUpdateCommand
     * @return
     */
    fun updateUserDetail(userDetailUpdateCommand: UserDetailUpdateCommand): User {

        val user = userRepository.findByUserId(userDetailUpdateCommand.userId) ?: throw NotFoundException()

        return userRepository.update(userDetailUpdateCommand.run {
            user.copy(userName = userName, birthday = birthday)
        })
    }
}
