package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.defaultUserLogin
import com.ikkun2501.bookmanagement.deleteAll
import com.ikkun2501.bookmanagement.domain.UserRepository
import com.ikkun2501.bookmanagement.insertDefaultUser
import com.ikkun2501.bookmanagement.usecase.command.user.UserDetailUpdateParams
import com.ikkun2501.bookmanagement.usecase.command.user.UserRegisterParams
import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate
import javax.inject.Inject
import javax.sql.DataSource
import javax.validation.ConstraintViolationException

/**
 * UserControllerTest
 */
@MicronautTest
internal class UserControllerTest {
    @Inject
    lateinit var dataSource: DataSource

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var userClient: UserClient

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var dsl: DSLContext

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val passwordEncoder = BCryptPasswordEncoder()

    /**
     * 登録テスト
     */
    @Test
    fun register() {

        dbSetup(dataSource) {
            deleteAll()
        }.launch()

        val userRegisterParams = UserRegisterParams(
            loginId = "user",
            password = "password",
            confirmPassword = "password",
            userName = "user",
            birthday = LocalDate.now()
        )

        val returnUser = userClient.register(userRegisterParams)

        assertAll(
            { assertEquals(userRegisterParams.loginId, returnUser.loginId) },
            { assertEquals(userRegisterParams.userName, returnUser.userName) },
            { assertIterableEquals(listOf("ROLE_USER"), returnUser.roles) },
            { assertTrue(returnUser.password.matches(userRegisterParams.password)) }
        )

        val dbUser = userRepository.findByUserId(returnUser.userId.value)!!
        assertEquals(returnUser, dbUser)
    }

    /**
     * パスワードが異なる場合
     */
    @Test
    fun register_password_different() {

        dbSetup(dataSource) {
            deleteAll()
        }.launch()

        val userRegisterParams = UserRegisterParams(
            loginId = "user",
            password = "password1",
            confirmPassword = "password2",
            userName = "user",
            birthday = LocalDate.now()
        )
        assertThrows(HttpClientResponseException::class.java) {
            userClient.register(userRegisterParams)
        }
    }

    /**
     * バリデーションが実施されることを確認する
     */
    @Test
    fun register_validate() {

        dbSetup(dataSource) {
            deleteAll()
        }.launch()

        val userRegisterParams = UserRegisterParams(
            loginId = "",
            password = "",
            confirmPassword = "",
            userName = "",
            birthday = LocalDate.now()
        )
        assertThrows(ConstraintViolationException::class.java) {
            userClient.register(userRegisterParams)
        }
    }

    @Test
    fun updateUserDetail() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        val params = UserDetailUpdateParams(
            userId = 1,
            userName = "userName2",
            birthday = LocalDate.of(2000, 1, 1)
        )

        val returnUser = userClient.detailUpdate(authClient.defaultUserLogin(), params)

        assertEquals(params.userId, returnUser.userId.value)
        assertEquals(params.userName, returnUser.userName)
        assertEquals(params.birthday, returnUser.birthday)

        val dbUser = userRepository.findByUserId(params.userId)!!

        assertEquals(returnUser, dbUser)
    }

    @Test
    fun updateUserDetail_validate() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        val params = UserDetailUpdateParams(
            userId = 1,
            userName = "",
            birthday = LocalDate.of(2000, 1, 1)
        )
        assertThrows(ConstraintViolationException::class.java) {
            userClient.detailUpdate(authClient.defaultUserLogin(), params)
        }
    }

    @Test
    fun updateUserDetail_notFound() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        val params = UserDetailUpdateParams(
            userId = 9999,
            userName = "userName",
            birthday = LocalDate.of(2000, 1, 1)
        )
        // 404 error
        // https://github.com/micronaut-projects/micronaut-core/issues/1188
        assertNull(userClient.detailUpdate(authClient.defaultUserLogin(), params))
    }
}
