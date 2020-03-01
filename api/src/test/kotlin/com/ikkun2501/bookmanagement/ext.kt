package com.ikkun2501.bookmanagement

import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.USER
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.USER_AUTHENTICATION
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.USER_AUTHORIZATION
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.USER_DETAIL
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserAuthenticationRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserAuthorizationRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserDetailRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserRecord
import com.ikkun2501.bookmanagement.interfaces.AuthClient
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import io.micronaut.security.authentication.UsernamePasswordCredentials
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate
import java.time.LocalDateTime

fun DbSetupBuilder.deleteAll() {
    this.deleteAllFrom(
        BOOK.name,
        AUTHOR.name,
        USER_AUTHORIZATION.name,
        USER_AUTHENTICATION.name,
        USER_DETAIL.name,
        USER.name
    )
}

private val passwordEncoder = BCryptPasswordEncoder()
fun DbSetupBuilder.insertDefaultUser() {
    insertInto(USER.name) {
        values(UserRecord(1, LocalDateTime.now()).intoMap())
    }
    insertInto(USER_DETAIL.name) {
        values(UserDetailRecord(1, "ユーザ名", LocalDate.now()).intoMap())
    }
    insertInto(USER_AUTHENTICATION.name) {
        values(
            UserAuthenticationRecord(
                1,
                "loginId",
                passwordEncoder.encode("password")
            ).intoMap()
        )
    }
    insertInto(USER_AUTHORIZATION.name) {
        values(UserAuthorizationRecord(1, "ROLE_ADMIN").intoMap())
        values(UserAuthorizationRecord(1, "ROLE_USER").intoMap())
    }
}

/**
 * デフォルトユーザでログイン
 *
 * @return Token
 */
fun AuthClient.defaultUserLogin(): String {
    return this
        .login(UsernamePasswordCredentials("loginId", "password"))
        .let { "Bearer ${it.accessToken}" }
}
