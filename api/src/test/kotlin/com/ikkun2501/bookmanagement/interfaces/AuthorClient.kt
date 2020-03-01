package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.interfaces.author.AuthorOperations
import io.micronaut.http.client.annotation.Client

/**
 * AuthorAPIのClientインターフェイス
 */
@Client("/author")
interface AuthorClient : AuthorOperations
