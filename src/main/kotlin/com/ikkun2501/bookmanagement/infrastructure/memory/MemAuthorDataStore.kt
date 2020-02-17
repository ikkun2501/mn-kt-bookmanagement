package com.ikkun2501.bookmanagement.infrastructure.memory

import com.ikkun2501.bookmanagement.domain.Author
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton

@Singleton
class MemAuthorDataStore {
    val map: MutableMap<String, Author> = ConcurrentHashMap(
        listOf(
            Author("1", "村上春樹", "あああ"),
            Author("2", "朝井リョウ", "いいい")
        ).associateBy { it.authorId }
    )

    fun get(authorId: String): Author? {
        return map[authorId]
    }

    fun findByIds(authorIds: List<String>): List<Author> {
        return map.values.filter { authorIds.contains(it.authorId) }
    }

    fun add(author: Author): Author {
        map += author.authorId to author
        return author
    }

    fun replace(author: Author): Author {
        map.replace(author.authorId, author)
        return author
    }

    fun remove(authorId: String): Author? {
        return map.remove(authorId)
    }

    fun search(keyword: String): List<Author> {
        return map.values.filter { it.authorName.contains(keyword) }
    }
}
