package com.ikkun2501.bookmanagement.interfaces

import com.github.javafaker.Faker
import com.ikkun2501.bookmanagement.defaultUserLogin
import com.ikkun2501.bookmanagement.deleteAll
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.AuthorRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.BookRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.toAuthorDetail
import com.ikkun2501.bookmanagement.insertDefaultUser
import com.ikkun2501.bookmanagement.interfaces.author.AuthorSaveRequest
import com.ikkun2501.bookmanagement.interfaces.author.AuthorUpdateRequest
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorQueryService
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchRequest
import com.ninja_squad.dbsetup_kotlin.dbSetup
import io.micronaut.test.annotation.MicronautTest
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.sql.DataSource
import javax.validation.ConstraintViolationException

/**
 * AuthorControllerTest
 */
@MicronautTest
internal class AuthorControllerTest {

    @Inject
    lateinit var dataSource: DataSource

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var authorClient: AuthorClient

    @Inject
    lateinit var authorQueryService: AuthorQueryService

    @Inject
    lateinit var dsl: DSLContext

    @Inject
    lateinit var faker: Faker

    private val logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 登録テスト
     */
    @Test
    fun save() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        val authorSaveParams = AuthorSaveRequest(
            authorName = faker.book().author(),
            description = faker.lorem().fixedString(500)
        )

        val returnAuthor = authorClient.save(authClient.defaultUserLogin(), authorSaveParams)

        assertEquals(authorSaveParams.authorName, returnAuthor.authorName)
        assertEquals(authorSaveParams.description, returnAuthor.authorDescription)

        val dbAuthor = authorQueryService.detail(returnAuthor.authorId)
        assertEquals(dbAuthor, returnAuthor)
    }

    /**
     * バリデーションの設定がされているかの確認
     */
    @Test
    fun save_validation() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        assertThrows<ConstraintViolationException>("") {
            authorClient.save(
                authClient.defaultUserLogin(), AuthorSaveRequest(authorName = "", description = "")
            )
        }
    }

    /**
     * 更新テスト
     */
    @Test
    fun update() {

        val authorId = 1
        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()

            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, faker.book().author(), faker.lorem().fixedString(500)).intoMap())
                values(AuthorRecord(2, faker.book().author(), faker.lorem().fixedString(500)).intoMap())
            }
        }.launch()

        val request = AuthorUpdateRequest(
            authorId = authorId,
            authorName = faker.book().author(),
            description = faker.lorem().fixedString(500)
        )

        val returnAuthor = authorClient.update(authClient.defaultUserLogin(), request)

        assertAll("Author",
            { assertEquals(request.authorId, returnAuthor.authorId) },
            { assertEquals(request.authorName, returnAuthor.authorName) },
            { assertEquals(request.description, returnAuthor.authorDescription) }
        )

        val dbAuthor = authorQueryService.detail(request.authorId)
        assertEquals(returnAuthor, dbAuthor)
    }

    /**
     * バリデーションの設定がされているかの確認
     */
    @Test
    fun update_validation() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
        }.launch()

        assertThrows<ConstraintViolationException>("") {
            authorClient.update(
                authClient.defaultUserLogin(),
                AuthorUpdateRequest(authorId = 1, authorName = "", description = faker.lorem().fixedString(500))
            )
        }
    }

    @Test
    fun delete() {

        val authorId = 1

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, faker.book().author(), faker.lorem().fixedString(500)).intoMap())
            }
        }.launch()

        authorClient.delete(authClient.defaultUserLogin(), authorId)

        assertEquals(0, dsl.fetchCount(AUTHOR))
    }

    /**
     * 詳細
     */
    @Test
    fun show() {

        val authorId = 1
        val books = listOf(
            BookRecord(1, 1, faker.book().title(), faker.lorem().fixedString(500)),
            BookRecord(2, 1, faker.book().title(), faker.lorem().fixedString(500)),
            BookRecord(3, 1, faker.book().title(), faker.lorem().fixedString(500))
        )
        val author = AuthorRecord(authorId, faker.book().author(), faker.lorem().fixedString(500))

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                books.forEach {
                    values(it.intoMap())
                }
            }
        }.launch()

        val returnAuthorDetail = authorClient.show(authClient.defaultUserLogin(), author.authorId)

        val expectedAuthorDetail = toAuthorDetail(author, books)

        assertEquals(expectedAuthorDetail, returnAuthorDetail)
    }

    @Test
    fun search_全件数() {

        val author = AuthorRecord(1, faker.book().author(), faker.lorem().fixedString(500))

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
        }.launch()

        val authorSearchResult = authorClient.search(
            authClient.defaultUserLogin(), AuthorSearchRequest(keyword = "", page = 1, limit = 100)
        )

        assertEquals(1, authorSearchResult.size)
    }

    @Test
    fun search_keyword() {

        val fullName = faker.name().fullName()
        val (lastName, firstName) = fullName.split(" ")

        val author = AuthorRecord(1, fullName, faker.lorem().fixedString(500))

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
        }.launch()

        val searchParams = AuthorSearchRequest(keyword = "", page = 1, limit = 100)

        assertEquals(
            1,
            authorClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = fullName)).size
        )
        assertEquals(
            1,
            authorClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = firstName)).size
        )
        assertEquals(
            1,
            authorClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = lastName)).size
        )
        assertEquals(
            0,
            authorClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = fullName + "1")).size
        )
    }

    @Test
    fun search_page_limit() {

        val author = AuthorRecord(1, faker.book().author(), faker.lorem().fixedString(500))

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                repeat(100) {
                    author.authorId = it + 1
                    println(author.toString())
                    values(author.intoMap())
                }
            }
        }.launch()

        val searchParams = AuthorSearchRequest(keyword = "", page = 2, limit = 10)
        val result = authorClient.search(authClient.defaultUserLogin(), searchParams)
        assertIterableEquals((11..20).toList(), result.map { it.authorId })
    }

    @Test
    fun search_sort() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名5", "").intoMap())
                values(AuthorRecord(2, "著者名4", "").intoMap())
                values(AuthorRecord(3, "著者名3", "").intoMap())
                values(AuthorRecord(4, "著者名2", "").intoMap())
                values(AuthorRecord(5, "著者名1", "").intoMap())
            }
        }.launch()

        val searchParams = AuthorSearchRequest(keyword = "", page = 1, limit = 10)
        val result = authorClient.search(authClient.defaultUserLogin(), searchParams)
        assertIterableEquals((5 downTo 1).toList(), result.map { it.authorId })
    }
}
