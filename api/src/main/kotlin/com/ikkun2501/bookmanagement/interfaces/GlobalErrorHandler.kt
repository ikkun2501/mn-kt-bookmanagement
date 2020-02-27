package com.ikkun2501.bookmanagement.interfaces

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.hateoas.Link
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import javax.inject.Singleton

/**
 * TODO エラーハンドリングの実装
 */
@Produces
@Singleton
@Requires(classes = [ExceptionHandler::class])
class GlobalErrorHandler : ExceptionHandler<Throwable, HttpResponse<JsonError>> {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(request: HttpRequest<*>, exception: Throwable): HttpResponse<JsonError> {
        logger.error("Internal Server Error", exception)
        val error = JsonError("Internal Server Error").link(Link.SELF, Link.of(request.uri))
        return HttpResponse.serverError(error)
    }
}
