package com.ikkun2501.bookmanagement

import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables
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
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
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
    insertInto(Tables.USER.name) {
        values(UserRecord(1, LocalDateTime.now()).intoMap())
    }
    insertInto(Tables.USER_DETAIL.name) {
        values(UserDetailRecord(1, "ユーザ名", LocalDate.now(), LocalDateTime.now()).intoMap())
    }
    insertInto(Tables.USER_AUTHENTICATION.name) {
        values(
            UserAuthenticationRecord(
                1,
                "loginId",
                passwordEncoder.encode("password"),
                LocalDateTime.now()
            ).intoMap()
        )
    }
    insertInto(Tables.USER_AUTHORIZATION.name) {
        values(UserAuthorizationRecord(1, "ROLE_ADMIN", LocalDateTime.now()).intoMap())
        values(UserAuthorizationRecord(1, "ROLE_USER", LocalDateTime.now()).intoMap())
    }
}
