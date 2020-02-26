package com.ikkun2501.bookmanagement.usecase.command.user

import com.ikkun2501.bookmanagement.domain.UserRepository
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import io.micronaut.spring.tx.annotation.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.inject.Singleton

@Transactional
@Singleton
class UserCommand(val userRepository: UserRepository) {

    val passwordEncoder = BCryptPasswordEncoder()

    fun auth(request: AuthenticationRequest<*, *>): AuthenticationResponse {
        val user = userRepository.findByLoginId(request.identity as String) ?: return AuthenticationFailed()

        if (!passwordEncoder.matches(request.secret as String, user.password)) {
            return AuthenticationFailed()
        }

        return UserDetails(
            user.userName,
            user.roles
        )
    }
}
