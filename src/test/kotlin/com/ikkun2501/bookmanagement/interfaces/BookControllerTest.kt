package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchParams
import com.ikkun2501.bookmanagement.usecase.query.book.BookSearchResultRow
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import javax.inject.Inject

/**
 * BookControllerTest
 */
@MicronautTest
internal class BookControllerTest {

    @Inject
    lateinit var client: BookClient

    @Test
    fun create() {
        val bookCreateParams = BookCreateParams(
            title = "a",
            authorId = "1",
            summary = "test"
        )
        val actualBook = client.create(bookCreateParams)
        assertEquals(bookCreateParams.title, actualBook.title)
        assertEquals(bookCreateParams.authorId, actualBook.authorId)
        assertEquals(bookCreateParams.summary, actualBook.summary)
    }

    @Test
    fun update() {

        val bookCreateParams = BookCreateParams(
            title = "a",
            authorId = "1",
            summary = "test"
        )
        val book = client.create(bookCreateParams)

        val bookUpdateParams = BookUpdateParams(
            bookId = book.bookId,
            title = "a",
            isbncode = "978-4-12-005171-5",
            authorId = "2"
        )
        val actualBook = client.update(bookUpdateParams)

        assertAll("Book",
            { assertEquals(bookUpdateParams.bookId, actualBook.bookId) },
            { assertEquals(bookUpdateParams.title, actualBook.title) },
            { assertEquals(bookUpdateParams.authorId, actualBook.authorId) }
        )
    }

    @Test
    fun delete() {
        val bookCreateParams = BookCreateParams(
            title = "aiueo",
            authorId = "1",
            summary = "test"
        )
        val book = client.create(bookCreateParams)

        client.delete(book.bookId)
    }

    @Test
    fun show() {
        val bookCreateParams = BookCreateParams(
            title = "aiueo",
            authorId = "1",
            summary = "test"
        )
        val book = client.create(bookCreateParams)

        val actualBook = client.show(book.bookId)

        val expected = BookSearchResultRow(
            bookId = book.bookId,
            summary = book.summary,
            title = book.title,
            authorName = "村上春樹",
            authorId = book.authorId
        )

        assertEquals(expected, actualBook)
    }

    @Test
    fun search() {

        // TODO AUTHORのデータが既にある前提でテストを書いている

        repeat(50) { i ->
            val bookCreateParams = BookCreateParams(
                title = "title${"%04d".format(i)}",
                authorId = "${i % 2 + 1}",
                summary = "summary${"%04d".format(i)}"
            )
            client.create(bookCreateParams)
        }

        val keyword = "春"
        val offset = 10
        val max = 10

        val bookSearchResult = client.search(
            BookSearchParams(
                keyword = keyword,
                offset = offset,
                max = max
            )
        )

        assertEquals(10, bookSearchResult.size)
        // assertEquals("title0022", bookSearchResult.first().title)
        // assertEquals("title0040", bookSearchResult.last().title)
    }
}
