package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.interfaces.user.UserOperations
import io.micronaut.http.client.annotation.Client

@Client("/users")
interface UserClient : UserOperations
