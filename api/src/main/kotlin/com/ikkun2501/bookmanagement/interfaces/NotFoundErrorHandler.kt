package com.ikkun2501.bookmanagement.interfaces

import com.ikkun2501.bookmanagement.domain.NotFoundException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.hateoas.Link
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Produces
@Singleton
@Requires(classes = [ExceptionHandler::class])
class NotFoundErrorHandler : ExceptionHandler<NotFoundException, HttpResponse<JsonError>> {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(request: HttpRequest<*>, exception: NotFoundException): HttpResponse<JsonError> {
        logger.warn("Not Found", exception)
        val error = JsonError("NotFound").link(Link.SELF, Link.of(request.uri))
        return HttpResponse.notFound(error)
    }
}
