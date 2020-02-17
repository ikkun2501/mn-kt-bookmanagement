package com.ikkun2501.bookmanagement.infrastructure

import com.ikkun2501.bookmanagement.domain.Book
import com.ikkun2501.bookmanagement.domain.BookRepository
import com.ikkun2501.bookmanagement.domain.NotFoundException
import com.ikkun2501.bookmanagement.infrastructure.memory.MemBookDataStore
import javax.inject.Singleton

@Singleton
class MemBookRepositoryImpl(val memBookDataStore: MemBookDataStore) :
    BookRepository {

    override fun findById(bookId: String): Book? {
        return memBookDataStore.get(bookId)
    }

    override fun create(book: Book): Book {
        return memBookDataStore.add(book)
    }

    override fun update(book: Book): Book {
        return memBookDataStore.replace(book)
    }

    override fun delete(bookId: String) {
        memBookDataStore.remove(bookId) ?: throw NotFoundException()
    }
}
