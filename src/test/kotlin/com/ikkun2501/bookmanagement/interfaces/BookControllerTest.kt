package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchParams
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchResultRow
import io.micronaut.http.uri.UriBuilder
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
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
            title = "aiueo",
            publishDate = LocalDate.now(),
            authorId = "1",
            summary = "test"
        )
        val actualBook = client.create(bookCreateParams)
        assertEquals(bookCreateParams.title, actualBook.title)
        assertEquals(bookCreateParams.publishDate, actualBook.publishDate)
        assertEquals(bookCreateParams.authorId, actualBook.authorId)
        assertEquals(bookCreateParams.summary, actualBook.summary)
    }

    @Test
    fun update() {

        val bookCreateParams = BookCreateParams(
            title = "aiueo",
            publishDate = LocalDate.now(),
            authorId = "1",
            summary = "test"
        )
        val book = client.create(bookCreateParams)

        val bookUpdateParams = BookUpdateParams(
            bookId = book.bookId,
            title = "あいうえお",
            publishDate = LocalDate.now(),
            authorId = "2",
            summary = "テスト"
        )
        val actualBook = client.update(bookUpdateParams)

        assertAll("Book",
            { assertEquals(bookUpdateParams.bookId, actualBook.bookId) },
            { assertEquals(bookUpdateParams.title, actualBook.title) },
            { assertEquals(bookUpdateParams.publishDate, actualBook.publishDate) },
            { assertEquals(bookUpdateParams.authorId, actualBook.authorId) },
            { assertEquals(bookUpdateParams.summary, actualBook.summary) }
        )
    }

    @Test
    fun delete() {
        val bookCreateParams = BookCreateParams(
            title = "aiueo",
            publishDate = LocalDate.now(),
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
            publishDate = LocalDate.now(),
            authorId = "1",
            summary = "test"
        )
        val book = client.create(bookCreateParams)

        val actualBook = client.show(book.bookId)

        val expected = BookSearchResultRow(
            bookId = book.bookId,
            summary = book.summary,
            title = book.title,
            publishDate = book.publishDate,
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
                publishDate = LocalDate.now(),
                authorId = "${i % 2 + 1}",
                summary = "summary${"%04d".format(i)}"
            )
            client.create(bookCreateParams)
        }

        val keyword = "春"
        val offset = 10
        val max = 10

        val url = UriBuilder.of("/book/search")
            .queryParam("keyword", keyword)
            .queryParam("offset", offset)
            .queryParam("max", max)
            .build()

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
