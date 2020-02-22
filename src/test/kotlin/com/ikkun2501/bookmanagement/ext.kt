package com.ikkun2501.bookmanagement

import com.example.db.jooq.gen.Tables.AUTHOR
import com.example.db.jooq.gen.Tables.BOOK
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder

fun DbSetupBuilder.deleteAll() {
    deleteAllFrom(BOOK.name, AUTHOR.name)
}
