package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.defaultUserLogin
import com.ikkun2501.bookmanagement.deleteAll
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.AuthorRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.BookRecord
import com.ikkun2501.bookmanagement.insertDefaultUser
import com.ikkun2501.bookmanagement.interfaces.book.BookSaveRequest
import com.ikkun2501.bookmanagement.interfaces.book.BookUpdateRequest
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookQueryService
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchRequest
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
 * BookControllerTest
 */
@MicronautTest
internal class BookControllerTest {

    @Inject
    lateinit var dataSource: DataSource

    @Inject
    lateinit var bookClient: BookClient

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var bookQueryService: BookQueryService

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
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名", "著者説明").intoMap())
            }
        }.launch()

        val bookSaveParams = BookSaveRequest(
            title = "タイトル",
            authorId = 1,
            description = "書籍説明"
        )

        val returnBookDetail = bookClient.save(authClient.defaultUserLogin(), bookSaveParams)

        assertEquals(bookSaveParams.title, returnBookDetail.title)
        assertEquals(bookSaveParams.authorId, returnBookDetail.authorId)
        assertEquals(bookSaveParams.description, returnBookDetail.bookDescription)
        assertEquals("著者説明", returnBookDetail.authorDescription)

        val dbBook = bookQueryService.detail(returnBookDetail.bookId)
        assertEquals(dbBook, returnBookDetail)
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
            bookClient.save(
                authClient.defaultUserLogin(), BookSaveRequest(title = "", authorId = 1, description = "書籍説明")
            )
        }
    }

    /**
     * 更新テスト
     */
    @Test
    fun update() {

        val bookId = 1
        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()

            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名１", "著者説明１").intoMap())
                values(AuthorRecord(2, "著者名２", "著者説明２").intoMap())
            }
            insertInto(BOOK.name) {
                values(BookRecord(bookId, 1, "タイトル", "書籍説明").intoMap())
            }
        }.launch()

        val request = BookUpdateRequest(
            bookId = bookId,
            title = "タイトル２",
            description = "",
            authorId = 2
        )

        val returnBook = bookClient.update(authClient.defaultUserLogin(), request)

        assertAll("Book",
            { assertEquals(request.bookId, returnBook.bookId) },
            { assertEquals(request.title, returnBook.title) },
            { assertEquals(request.authorId, returnBook.authorId) }
        )

        val dbBook = bookQueryService.detail(request.bookId)
        assertEquals(returnBook, dbBook)
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
            bookClient.update(
                authClient.defaultUserLogin(), BookUpdateRequest(bookId = 1, title = "", authorId = 1, description = "書籍説明")
            )
        }
    }

    @Test
    fun delete() {

        val bookId = 1

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名", "著者説明").intoMap())
            }
            insertInto(BOOK.name) {
                values(BookRecord(bookId, 1, "タイトル", "書籍説明").intoMap())
            }
        }.launch()

        bookClient.delete(authClient.defaultUserLogin(), bookId)

        assertEquals(0, dsl.fetchCount(BOOK))
    }

    /**
     * 詳細
     */
    @Test
    fun show() {

        val bookId = 1
        val book = BookRecord(bookId, 1, "タイトル", "書籍説明")
        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()

            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                values(book.intoMap())
            }
        }.launch()

        val returnBookDetail = bookClient.show(authClient.defaultUserLogin(), book.bookId)

        val expectedBookDetail = BookDetail(
            bookId = book.bookId,
            bookDescription = book.description,
            title = book.title,
            authorName = author.authorName,
            authorId = book.authorId,
            authorDescription = author.description
        )

        assertEquals(expectedBookDetail, returnBookDetail)
    }

    @Test
    fun search_全件数() {

        val bookId = 1
        val book = BookRecord(bookId, 1, "タイトル", "書籍説明")
        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                values(book.intoMap())
            }
        }.launch()

        val bookSearchResult = bookClient.search(
            authClient.defaultUserLogin(), BookSearchRequest(keyword = "", page = 1, limit = 100)
        )

        assertEquals(1, bookSearchResult.size)
    }

    @Test
    fun search_keyword() {

        val bookId = 1
        val book = BookRecord(bookId, 1, "タイトル", "書籍説明")
        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                values(book.intoMap())
            }
        }.launch()

        val searchParams = BookSearchRequest(keyword = "", page = 1, limit = 100)

        assertEquals(1, bookClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "タイトル")).size)
        assertEquals(1, bookClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "タイ")).size)
        assertEquals(1, bookClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "トル")).size)
        assertEquals(1, bookClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "著者名")).size)
        assertEquals(1, bookClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "著者")).size)
        assertEquals(1, bookClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "者名")).size)

        assertEquals(0, bookClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "タイトル1")).size)
        assertEquals(0, bookClient.search(authClient.defaultUserLogin(), searchParams.copy(keyword = "著者名1")).size)
    }

    @Test
    fun search_page_limit() {

        val book = BookRecord(null, 1, "タイトル", "書籍説明")
        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                repeat(100) {
                    book.bookId = it + 1
                    println(book.toString())
                    values(book.intoMap())
                }
            }
        }.launch()

        val searchParams = BookSearchRequest(keyword = "", page = 2, limit = 10)
        val result = bookClient.search(authClient.defaultUserLogin(), searchParams)
        assertIterableEquals((11..20).toList(), result.map { it.bookId })
    }

    @Test
    fun search_sort() {

        dbSetup(dataSource) {
            deleteAll()
            insertDefaultUser()
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名", "著者説明").intoMap())
            }
            insertInto(BOOK.name) {
                values(BookRecord(1, 1, "タイトル5", "書籍説明").intoMap())
                values(BookRecord(2, 1, "タイトル4", "書籍説明").intoMap())
                values(BookRecord(3, 1, "タイトル3", "書籍説明").intoMap())
                values(BookRecord(4, 1, "タイトル2", "書籍説明").intoMap())
                values(BookRecord(5, 1, "タイトル1", "書籍説明").intoMap())
            }
        }.launch()

        val searchParams = BookSearchRequest(keyword = "", page = 1, limit = 10)
        val result = bookClient.search(authClient.defaultUserLogin(), searchParams)
        assertIterableEquals((5 downTo 1).toList(), result.map { it.bookId })
    }
}
