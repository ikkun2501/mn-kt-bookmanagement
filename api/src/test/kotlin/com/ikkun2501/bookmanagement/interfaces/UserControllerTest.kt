package com.ikkun2501.bookmanagement.interfaces

import com.github.javafaker.Faker
import com.ikkun2501.bookmanagement.defaultUserLogin
import com.ikkun2501.bookmanagement.deleteAll
import com.ikkun2501.bookmanagement.insertDefaultUser
import com.ikkun2501.bookmanagement.interfaces.user.UserDetailUpdateRequest
import com.ikkun2501.bookmanagement.interfaces.user.UserSaveRequest
import com.ikkun2501.bookmanagement.usecase.query.user.UserQueryService
import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
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
    lateinit var userClient: UserClient

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var userQueryService: UserQueryService

    @Inject
    lateinit var faker: Faker

    /**
     * 登録テスト
     */
    @Test
    fun save() {

        dbSetup(dataSource) {
            deleteAll()
        }.launch()

        val password = faker.internet().password()
        val request = UserSaveRequest(
            loginId = faker.random().hex(),
            password = password,
            confirmPassword = password,
            userName = faker.funnyName().name(),
            birthday = LocalDate.now()
        )

        val returnUser = userClient.save(request)

        assertAll(
            { assertEquals(request.userName, returnUser.userName) },
            { assertEquals(returnUser.birthday, request.birthday) }
        )

        val dbUser = userQueryService.detail(returnUser.userId)
        assertEquals(returnUser, dbUser)
    }

    /**
     * パスワードが異なる場合
     */
    @Test
    fun save_password_different() {

        dbSetup(dataSource) {
            deleteAll()
        }.launch()

        val userSaveRequest = UserSaveRequest(
            loginId = faker.random().hex(),
            password = "password1",
            confirmPassword = "password2",
            userName = faker.funnyName().name(),
            birthday = LocalDate.now()
        )
        assertThrows(HttpClientResponseException::class.java) {
            userClient.save(userSaveRequest)
        }
    }

    /**
     * バリデーションが実施されることを確認する
     */
    @Test
    fun save_validate() {

        dbSetup(dataSource) {
            deleteAll()
        }.launch()

        val userSaveRequest = UserSaveRequest(
            loginId = "",
            password = "",
            confirmPassword = "",
            userName = "",
            birthday = LocalDate.now()
        )
        assertThrows(ConstraintViolationException::class.java) {
            userClient.save(userSaveRequest)
        }
    }

    @Test
    fun updateUserDetail() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        val request = UserDetailUpdateRequest(
            userId = 1,
            userName = faker.funnyName().name(),
            birthday = LocalDate.of(2000, 1, 1)
        )

        val returnUser = userClient.detailUpdate(authClient.defaultUserLogin(), request)

        assertEquals(request.userId, returnUser.userId)
        assertEquals(request.userName, returnUser.userName)
        assertEquals(request.birthday, returnUser.birthday)

        val dbUser = userQueryService.detail(request.userId)

        assertEquals(returnUser, dbUser)
    }

    @Test
    fun updateUserDetail_validate() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        val request = UserDetailUpdateRequest(
            userId = 1,
            userName = "",
            birthday = LocalDate.of(2000, 1, 1)
        )
        assertThrows(ConstraintViolationException::class.java) {
            userClient.detailUpdate(authClient.defaultUserLogin(), request)
        }
    }

    @Test
    fun updateUserDetail_notFound() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        val request = UserDetailUpdateRequest(
            userId = 9999,
            userName = faker.funnyName().name(),
            birthday = LocalDate.of(2000, 1, 1)
        )
        // 404 error
        // https://github.com/micronaut-projects/micronaut-core/issues/1188
        assertNull(userClient.detailUpdate(authClient.defaultUserLogin(), request))
    }
}
