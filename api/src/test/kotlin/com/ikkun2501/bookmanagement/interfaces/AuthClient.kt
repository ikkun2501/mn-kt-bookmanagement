package com.ikkun2501.bookmanagement.interfaces

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken

@Client("/login")
interface AuthClient {

    @Post()
    fun login(@Body request: UsernamePasswordCredentials): BearerAccessRefreshToken
}
