package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.usecase.command.user.UserCommandService
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword(@Inject private val userCommandService: UserCommandService) : AuthenticationProvider {
    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        return Flowable.just(userCommandService.auth(authenticationRequest))
    }
}
