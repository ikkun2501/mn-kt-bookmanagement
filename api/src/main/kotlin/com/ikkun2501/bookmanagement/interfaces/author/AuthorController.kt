package com.ikkun2501.bookmanagement.interfaces.author

import com.ikkun2501.bookmanagement.domain.Author
import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.usecase.command.author.AuthorCommandService
import com.ikkun2501.bookmanagement.usecase.command.author.AuthorSaveParams
import com.ikkun2501.bookmanagement.usecase.command.author.AuthorUpdateParams
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorDetail
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorQueryService
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchParams
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchResultRow
import io.micronaut.http.annotation.Controller
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/author")
class AuthorController(
    private val authorCommandService: AuthorCommandService,
    private val authorQueryService: AuthorQueryService
) : AuthorOperations {
    override fun save(token: String, authorSaveParams: AuthorSaveParams): Author {
        return authorCommandService.save(authorSaveParams)
    }

    override fun update(token: String, authorUpdateParams: AuthorUpdateParams): Author {
        return authorCommandService.update(authorUpdateParams)
    }

    override fun show(token: String, authorId: Int): AuthorDetail {
        return authorQueryService.detail(authorId)
    }

    override fun search(token: String, authorSearchParams: AuthorSearchParams): List<AuthorSearchResultRow> {
        return authorQueryService.search(authorSearchParams)
    }

    override fun delete(token: String, authorId: Int) {
        authorCommandService.delete(SequenceId(authorId))
    }
}
