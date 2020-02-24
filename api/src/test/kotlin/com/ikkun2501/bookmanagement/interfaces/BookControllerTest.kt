package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.AuthorRecord
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.tables.records.BookRecord
import com.ikkun2501.bookmanagement.deleteAll
import com.ikkun2501.bookmanagement.infrastructure.jooq.toObject
import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookDetail
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchParams
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
    lateinit var client: BookClient

    @Inject
    lateinit var dsl: DSLContext

    private val logger = LoggerFactory.getLogger(this.javaClass)
    /**
     * 登録テスト
     */
    @Test
    fun create() {

        dbSetup(dataSource) {
            deleteAll()
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名", "著者説明").intoMap())
            }
        }.launch()

        val bookCreateParams = BookCreateParams(
            title = "タイトル",
            authorId = 1,
            description = "書籍説明"
        )

        val returnBook = client.create(bookCreateParams)

        assertEquals(bookCreateParams.title, returnBook.title)
        assertEquals(bookCreateParams.authorId, returnBook.authorId)
        assertEquals(bookCreateParams.description, returnBook.description)

        val dbBook = dsl.fetchOne(BOOK, BOOK.BOOK_ID.eq(returnBook.bookId)).toObject()
        assertEquals(dbBook, returnBook)
    }

    /**
     * バリデーションの設定がされているかの確認
     */
    @Test
    fun create_validation() {

        dbSetup(dataSource) {
            deleteAll()
        }.launch()

        assertThrows<ConstraintViolationException>("") {
            client.create(
                BookCreateParams(title = "", authorId = 1, description = "書籍説明")
            )
        }
    }

    /**
     * 更新テスト
     */
    @Test
    fun update() {

        val bookId = 1L
        dbSetup(dataSource) {
            deleteAll()
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名１", "著者説明１").intoMap())
                values(AuthorRecord(2, "著者名２", "著者説明２").intoMap())
            }
            insertInto(BOOK.name) {
                values(BookRecord(bookId, 1, "タイトル", "書籍説明").intoMap())
            }
        }.launch()

        val bookUpdateParams = BookUpdateParams(
            bookId = bookId,
            title = "タイトル２",
            description = "書籍説明２",
            authorId = 2
        )

        val returnBook = client.update(bookUpdateParams)

        assertAll("Book",
            { assertEquals(bookUpdateParams.bookId, returnBook.bookId) },
            { assertEquals(bookUpdateParams.title, returnBook.title) },
            { assertEquals(bookUpdateParams.authorId, returnBook.authorId) }
        )

        val dbBook = dsl.fetchOne(BOOK, BOOK.BOOK_ID.eq(bookId)).toObject()
        assertEquals(returnBook, dbBook)
    }

    /**
     * バリデーションの設定がされているかの確認
     */
    @Test
    fun update_validation() {

        dbSetup(dataSource) {
            deleteAll()
        }.launch()

        assertThrows<ConstraintViolationException>("") {
            client.update(
                BookUpdateParams(bookId = 1, title = "", authorId = 1, description = "書籍説明")
            )
        }
    }

    @Test
    fun delete() {

        val bookId = 1L

        dbSetup(dataSource) {
            deleteAll()
            insertInto(AUTHOR.name) {
                values(AuthorRecord(1, "著者名", "著者説明").intoMap())
            }
            insertInto(BOOK.name) {
                values(BookRecord(bookId, 1, "タイトル", "書籍説明").intoMap())
            }
        }.launch()

        client.delete(bookId)

        assertEquals(0, dsl.fetchCount(BOOK))
    }

    /**
     * 詳細
     */
    @Test
    fun show() {

        val bookId = 1L
        val book = BookRecord(bookId, 1, "タイトル", "書籍説明")
        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                values(book.intoMap())
            }
        }.launch()

        val returnBookDetail = client.show(book.bookId)

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

        val bookId = 1L
        val book = BookRecord(bookId, 1, "タイトル", "書籍説明")
        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                values(book.intoMap())
            }
        }.launch()

        val bookSearchResult = client.search(
            BookSearchParams(keyword = "", page = 1, limit = 100)
        )

        assertEquals(1, bookSearchResult.size)
    }

    @Test
    fun search_keyword() {

        val bookId = 1L
        val book = BookRecord(bookId, 1, "タイトル", "書籍説明")
        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                values(book.intoMap())
            }
        }.launch()

        val searchParams = BookSearchParams(keyword = "", page = 1, limit = 100)

        assertEquals(1, client.search(searchParams.copy(keyword = "タイトル")).size)
        assertEquals(1, client.search(searchParams.copy(keyword = "タイ")).size)
        assertEquals(1, client.search(searchParams.copy(keyword = "トル")).size)
        assertEquals(1, client.search(searchParams.copy(keyword = "著者名")).size)
        assertEquals(1, client.search(searchParams.copy(keyword = "著者")).size)
        assertEquals(1, client.search(searchParams.copy(keyword = "者名")).size)

        assertEquals(0, client.search(searchParams.copy(keyword = "タイトル1")).size)
        assertEquals(0, client.search(searchParams.copy(keyword = "著者名1")).size)
    }

    @Test
    fun search_page_limit() {

        val book = BookRecord(null, 1, "タイトル", "書籍説明")
        val author = AuthorRecord(1, "著者名", "著者説明")

        dbSetup(dataSource) {
            deleteAll()
            insertInto(AUTHOR.name) {
                values(author.intoMap())
            }
            insertInto(BOOK.name) {
                repeat(100) {
                    book.bookId = it.toLong() + 1
                    println(book.toString())
                    values(book.intoMap())
                }
            }
        }.launch()

        val searchParams = BookSearchParams(keyword = "", page = 2, limit = 10)
        val result = client.search(searchParams)
        assertIterableEquals((11L..20L).toList(), result.map { it.bookId })
    }

    @Test
    fun search_sort() {

        dbSetup(dataSource) {
            deleteAll()
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

        val searchParams = BookSearchParams(keyword = "", page = 1, limit = 10)
        val result = client.search(searchParams)
        assertIterableEquals((5L downTo 1L).toList(), result.map { it.bookId })
    }
}
