package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.defaultUserLogin
import com.ikkun2501.bookmanagement.deleteAll
import com.ikkun2501.bookmanagement.domain.AuthorRepository
import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.AuthorRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.BookRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.toBookInfo
import com.ikkun2501.bookmanagement.insertDefaultUser
import com.ikkun2501.bookmanagement.usecase.command.author.AuthorSaveParams
import com.ikkun2501.bookmanagement.usecase.command.author.AuthorUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorDetail
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchParams
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
    lateinit var authorRepository: AuthorRepository

    @Inject
    lateinit var dsl: DSLContext

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

        val authorSaveParams = AuthorSaveParams(
            authorName = "著者名",
            description = "書籍説明"
        )

        val returnAuthor = authorClient.save(authClient.defaultUserLogin(), authorSaveParams)

        assertEquals(authorSaveParams.authorName, returnAuthor.authorName)
        assertEquals(authorSaveParams.description, returnAuthor.description)

        val dbAuthor = authorRepository.findById(returnAuthor.authorId)!!
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
                authClient.defaultUserLogin(), AuthorSaveParams(authorName = "", description = "")
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
                values(AuthorRecord(1, "著者名１", "著者説明１").intoMap())
                values(AuthorRecord(2, "著者名２", "著者説明２").intoMap())
            }
        }.launch()

        val authorUpdateParams = AuthorUpdateParams(
            authorId = authorId,
            authorName = "著者名",
            description = "著者説明"
        )

        val returnAuthor = authorClient.update(authClient.defaultUserLogin(), authorUpdateParams)

        assertAll("Author",
            { assertEquals(authorUpdateParams.authorId, returnAuthor.authorId.value) },
            { assertEquals(authorUpdateParams.authorName, returnAuthor.authorName) },
            { assertEquals(authorUpdateParams.description, returnAuthor.description) }
        )

        val dbAuthor = authorRepository.findById(SequenceId(authorUpdateParams.authorId))!!
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
                AuthorUpdateParams(authorId = 1, authorName = "", description = "著者説明")
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
                values(AuthorRecord(1, "著者名", "著者説明").intoMap())
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
            BookRecord(1, 1, "タイトル1", "書籍説明1"),
            BookRecord(2, 1, "タイトル2", "書籍説明2"),
            BookRecord(3, 1, "タイトル3", "書籍説明3")
        )
        val author = AuthorRecord(authorId, "著者名", "著者説明")

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

        val expectedAuthorDetail = AuthorDetail(
            authorId = author.authorId,
            authorName = author.authorName,
            authorDescription = author.description,
            books = books.map(BookRecord::toBookInfo)
        )

        assertEquals(expectedAuthorDetail, returnAuthorDetail)
    }

    @Test
    fun search_全件数() {

        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
        }.launch()

        val authorSearchResult = authorClient.search(
            authClient.defaultUserLogin(), AuthorSearchParams(keyword = "", page = 1, limit = 100)
        )

        assertEquals(1, authorSearchResult.size)
    }

    @Test
    fun search_keyword() {

        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
        }.launch()

        val searchParams = AuthorSearchParams(keyword = "", page = 1, limit = 100)

        assertEquals(1, authorClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "著者")).size)
        assertEquals(1, authorClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "者名")).size)
        assertEquals(0, authorClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "著者名1")).size)
    }

    @Test
    fun search_page_limit() {

        val author = AuthorRecord(1, "著者名", "著者説明")

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

        val searchParams = AuthorSearchParams(keyword = "", page = 2, limit = 10)
        val result = authorClient.search(authClient.defaultUserLogin(), searchParams)
        assertIterableEquals((11..20).toList(), result.map { it.authorId })
    }

    @Test
    fun search_sort() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名5", "著者説明").intoMap())
                values(AuthorRecord(2, "著者名4", "著者説明").intoMap())
                values(AuthorRecord(3, "著者名3", "著者説明").intoMap())
                values(AuthorRecord(4, "著者名2", "著者説明").intoMap())
                values(AuthorRecord(5, "著者名1", "著者説明").intoMap())
            }
        }.launch()

        val searchParams = AuthorSearchParams(keyword = "", page = 1, limit = 10)
        val result = authorClient.search(authClient.defaultUserLogin(), searchParams)
        assertIterableEquals((5 downTo 1).toList(), result.map { it.authorId })
    }
}
