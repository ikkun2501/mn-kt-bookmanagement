package com.ikkun2501.bookmanagement.interfaces

import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.hateoas.Link
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.validation.exceptions.ConstraintExceptionHandler
import javax.inject.Singleton
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.ElementKind
import javax.validation.Path

@Primary
@Produces
@Singleton
@Requires(classes = [ConstraintViolationException::class, ExceptionHandler::class])
class OriginalConstraintExceptionHandler : ConstraintExceptionHandler() {

    override fun handle(
        request: HttpRequest<*>,
        exception: ConstraintViolationException
    ): HttpResponse<JsonError> {
        val constraintViolations = exception.constraintViolations
        return if (constraintViolations == null || constraintViolations.isEmpty()) {
            val error =
                JsonError(if (exception.message == null) HttpStatus.BAD_REQUEST.reason else exception.message)
            error.link(Link.SELF, Link.of(request.uri))
            HttpResponse.badRequest(error)
        } else {
            // TODO 仮実装 エラーフォーマット、メッセージの設定を考慮する
            val message = constraintViolations.joinToString(",") { buildMessage(it) }
            val error = JsonError(message)
            error.link(Link.SELF, Link.of(request.uri))
            HttpResponse.badRequest(error)
        }
    }

    /**
     * Builds a message based on the provided violation.
     *
     * @param violation The constraint violation
     * @return The violation message
     */
    override fun buildMessage(violation: ConstraintViolation<*>): String {
        val propertyPath = violation.propertyPath
        val message = StringBuilder()
        val i: Iterator<Path.Node> = propertyPath.iterator()
        while (i.hasNext()) {
            val node = i.next()
            if (node.kind == ElementKind.METHOD || node.kind == ElementKind.CONSTRUCTOR) {
                continue
            }
            message.append(node.name)
            if (i.hasNext()) {
                message.append('.')
            }
        }
        message.append(": ").append(violation.message)
        return message.toString()
    }
}
