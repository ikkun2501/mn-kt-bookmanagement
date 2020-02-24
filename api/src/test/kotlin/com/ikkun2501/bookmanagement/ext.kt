package com.ikkun2501.bookmanagement

import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.AUTHOR
import com.ikkun2501.bookmanagement.infrastructure.jooq.gen.Tables.BOOK
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder

fun DbSetupBuilder.deleteAll() {
    deleteAllFrom(BOOK.name, AUTHOR.name)
}
