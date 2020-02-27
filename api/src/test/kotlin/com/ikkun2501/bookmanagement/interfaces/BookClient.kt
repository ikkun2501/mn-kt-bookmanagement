package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.interfaces.book.BookOperations
import io.micronaut.http.client.annotation.Client

/**
 * BookAPIのClientインターフェイス
 * MEMO このインターフェイスからコンパイル時に実装が生成される
 */
@Client("/book")
interface BookClient : BookOperations
