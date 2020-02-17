package com.ikkun2501.bookmanagement.infrastructure.memory

import com.ikkun2501.bookmanagement.domain.Book
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton

@Singleton
class MemBookDataStore {

    val map: MutableMap<String, Book> = ConcurrentHashMap()

    fun get(bookId: String): Book? {
        return map[bookId]
    }

    fun add(book: Book): Book {
        map += book.bookId to book
        return book
    }

    fun replace(book: Book): Book {
        map.replace(book.bookId, book)
        return book
    }

    fun remove(bookId: String): Book? {
        return map.remove(bookId)
    }

    fun search(keyword: String, authorIds: List<String>): List<Book> {
        return map.values.filter { authorIds.contains(it.authorId) || it.title.contains(keyword) }
    }
}
