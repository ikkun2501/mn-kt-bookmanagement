package com.ikkun2501.bookmanagement.usecase.command.book

import io.micronaut.test.annotation.MicronautTest
import io.micronaut.validation.validator.Validator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class BookUpdateParamsTest {

    @Inject
    lateinit var validator: Validator

    @Test
    fun notBlankTest() {
        val params = BookUpdateParams(
            bookId = 1L,
            authorId = 1L,
            description = "",
            title = ""
        )

        val constraintViolations = validator.validate(params)

        assertEquals(1, constraintViolations.size)
        val violation = constraintViolations.first()
        assertEquals("title", violation.propertyPath.toString())
        assertEquals("must not be blank", violation.message)
    }

    @Test
    fun maxTest() {
        val params = BookUpdateParams(
            bookId = 1L,
            authorId = 1L,
            description = "1234567890".repeat(200) + "1",
            title = "title"
        )
        val constraintViolations = validator.validate(params)

        assertEquals(1, constraintViolations.size)
        val violation = constraintViolations.first()
        assertEquals("description", violation.propertyPath.toString())
        assertEquals("size must be between 0 and 2000", violation.message)
    }
}
