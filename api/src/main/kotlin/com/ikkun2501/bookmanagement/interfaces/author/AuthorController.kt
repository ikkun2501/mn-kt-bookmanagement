package com.ikkun2501.bookmanagement.interfaces.author

import com.ikkun2501.bookmanagement.domain.SequenceId
import com.ikkun2501.bookmanagement.usecase.command.author.AuthorCommandService
import com.ikkun2501.bookmanagement.usecase.command.author.AuthorSaveCommand
import com.ikkun2501.bookmanagement.usecase.command.author.AuthorUpdateCommand
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorDetail
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorQueryService
import com.ikkun2501.bookmanagement.usecase.query.author.AuthorSearchRequest
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
    override fun save(token: String, request: AuthorSaveRequest): AuthorDetail {
        val command = request.run {
            AuthorSaveCommand(
                authorName = authorName,
                description = description
            )
        }
        return authorCommandService.save(command).run {
            authorQueryService.detail(this.authorId.value)
        }
    }

    override fun update(token: String, request: AuthorUpdateRequest): AuthorDetail {
        val command = request.run {
            AuthorUpdateCommand(
                authorId = SequenceId(authorId),
                authorName = authorName,
                description = description
            )
        }

        return authorCommandService.update(command).run {
            authorQueryService.detail(this.authorId.value)
        }
    }

    override fun show(token: String, authorId: Int): AuthorDetail {
        return authorQueryService.detail(authorId)
    }

    override fun search(token: String, request: AuthorSearchRequest): List<AuthorSearchResultRow> {
        return authorQueryService.search(request)
    }

    override fun delete(token: String, authorId: Int) {
        authorCommandService.delete(SequenceId(authorId))
    }
}
