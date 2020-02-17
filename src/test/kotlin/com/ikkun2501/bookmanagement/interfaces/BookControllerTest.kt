package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.usecase.command.book.BookCreateParams
import com.ikkun2501.bookmanagement.usecase.command.book.BookUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.query.BookSearchResultRow
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import javax.inject.Inject

@MicronautTest
internal class BookControllerTest {

    // QUESTION コンストラクタインジェクションできない
    @Inject
    @field:Client("/")
    lateinit var client: RxHttpClient

    // TODO Nestedしたテストはまだサポートされていない
    // @Nested
    // inner class CreateTest{
    // }

    @Test
    fun create() {
        val bookCreateParams = BookCreateParams(
            title = "aiueo",
            publishDate = LocalDate.now(),
            authorId = "1",
            summary = "test"
        )
        val result =
            client.toBlocking().exchange(HttpRequest.POST("/book", bookCreateParams), Argument.of(Book::class.java))

        assertEquals(HttpStatus.CREATED, result.status)

        val actualBook = result.body()!!
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
        val book =
            client.toBlocking().exchange(
                HttpRequest.POST("/book", bookCreateParams),
                Argument.of(Book::class.java)
            ).body()!!

        val bookUpdateParams = BookUpdateParams(
            bookId = book.bookId,
            title = "あいうえお",
            publishDate = LocalDate.now(),
            authorId = "2",
            summary = "テスト"
        )
        val result = client.toBlocking().exchange(HttpRequest.PUT("/book", bookUpdateParams), Book::class.java)

        assertEquals(HttpStatus.OK, result.status)

        val actualBook = result.body()!!
        assertAll("Book",
            { assertEquals(bookUpdateParams.bookId, actualBook.bookId) },
            { assertEquals(bookUpdateParams.title, actualBook.title) },
            { assertEquals(bookUpdateParams.publishDate, actualBook.publishDate) },
            { assertEquals(bookUpdateParams.authorId, actualBook.authorId) },
            { assertEquals(bookUpdateParams.summary, actualBook.summary) }
        )
    }

    @Test
    fun deleteTest() {
        val bookCreateParams = BookCreateParams(
            title = "aiueo",
            publishDate = LocalDate.now(),
            authorId = "1",
            summary = "test"
        )
        val book =
            client.toBlocking().exchange(HttpRequest.POST("/book", bookCreateParams), Book::class.java).body()!!

        val result = client.toBlocking().exchange<Void, Void>(HttpRequest.DELETE("/book/${book.bookId}"))

        assertEquals(HttpStatus.NO_CONTENT, result.status)
    }

    @Test
    fun show() {
        val bookCreateParams = BookCreateParams(
            title = "aiueo",
            publishDate = LocalDate.now(),
            authorId = "1",
            summary = "test"
        )
        val book =
            client.toBlocking().exchange(HttpRequest.POST("/book", bookCreateParams), Book::class.java).body()!!

        // QUESTION このジェネリクスの書き方が正しいのか不明
        val result = client.toBlocking()
            .exchange<Void, BookSearchResultRow>(
                HttpRequest.GET("/book/${book.bookId}"),
                BookSearchResultRow::class.java
            )

        assertEquals(HttpStatus.OK, result.status)

        val expected = BookSearchResultRow(
            bookId = book.bookId,
            summary = book.summary,
            title = book.title,
            publishDate = book.publishDate,
            authorName = "村上春樹",
            authorId = book.authorId
        )

        assertEquals(expected, result.body()!!)
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
            client.toBlocking().exchange(
                HttpRequest.POST("/book", bookCreateParams),
                Argument.of(Book::class.java)
            ).body()!!
        }

        val keyword = "春"
        val offset = 10
        val max = 10

        val url = UriBuilder.of("/book/search")
            .queryParam("keyword", keyword)
            .queryParam("offset", offset)
            .queryParam("max", max)
            .build()

        val result = client.toBlocking()
            .exchange<Void, List<BookSearchResultRow>>(
                HttpRequest.GET(url),
                Argument.listOf(BookSearchResultRow::class.java)
            )

        val bookSearchResult = result.body()!!
        assertEquals(10, bookSearchResult.size)
        // assertEquals("title0022", bookSearchResult.first().title)
        // assertEquals("title0040", bookSearchResult.last().title)
    }
}
