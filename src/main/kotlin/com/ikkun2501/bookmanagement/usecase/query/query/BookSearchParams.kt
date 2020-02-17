package com.ikkun2501.bookmanagement.usecase.query.query

import io.micronaut.core.annotation.Introspected

/**
 * 検索パラメータ
 * MEMO Introspectedアノテーションを付けるとBeanInstropectorの管理対象になり、イミュータブルなオブジェクトにできる
 * @property keyword
 * @property offset
 * @property max
 */
@Introspected
data class BookSearchParams(
    val keyword: String,
    val offset: Int,
    val max: Int

)