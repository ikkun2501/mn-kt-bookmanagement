package com.ikkun2501.bookmanagement.usecase.query.book

import io.micronaut.core.annotation.Introspected

/**
 * 書籍検索パラメータ
 * MEMO Introspectedアノテーションを付けるとBeanIntrospectorの管理対象になり、
 *      GetParameterでもイミュータブルなオブジェクトのまま使用できる
 * @property keyword 検索キーワード
 * @property page ページ数 1オリジン
 * @property limit 取得数
 */
@Introspected
data class BookSearchParams(
    val keyword: String,
    val page: Int,
    val limit: Int
)
