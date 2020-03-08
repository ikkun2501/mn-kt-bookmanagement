package com.ikkun2501.bookmanagement.interfaces

import com.github.javafaker.Faker
import com.ikkun2501.bookmanagement.deleteAll
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserAuthenticationRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserAuthorizationRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserDetailRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.UserRecord
import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.sql.DataSource

@MicronautTest
internal class AuthenticationProviderUserPasswordTest {

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var dataSource: DataSource

    @Inject
    lateinit var faker: Faker

    private val passwordEncoder = BCryptPasswordEncoder()

    @Test
    fun auth() {

        val loginId = faker.random().hex()
        val password = faker.internet().password()
        val username = faker.funnyName().name()
        dbSetup(dataSource) {
            deleteAll()
            insertInto(Tables.USER.name) {
                values(UserRecord(1, LocalDateTime.now()).intoMap())
            }
            insertInto(Tables.USER_DETAIL.name) {
                values(UserDetailRecord(1, username, LocalDate.now()).intoMap())
            }
            insertInto(Tables.USER_AUTHENTICATION.name) {
                values(
                    UserAuthenticationRecord(
                        1,
                        loginId,
                        passwordEncoder.encode(password)
                    ).intoMap()
                )
            }
            insertInto(Tables.USER_AUTHORIZATION.name) {
                values(UserAuthorizationRecord(1, "ROLE_ADMIN").intoMap())
                values(UserAuthorizationRecord(1, "ROLE_USER").intoMap())
            }
        }.launch()

        val token = authClient.login(UsernamePasswordCredentials(loginId, password))

        assertEquals(username, token.username)
        assertIterableEquals(listOf("ROLE_ADMIN", "ROLE_USER"), token.roles)
    }
}
