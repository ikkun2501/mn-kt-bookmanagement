package com.ikkun2501.bookmanagement.interfaces

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme

@OpenAPIDefinition(
    info = Info(
        title = "mn-kotlin-openapi-app",
        version = "1.0",
        description = "書籍管理API"
    )
)
@SecurityScheme(name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "jwt")
object OpenAPIDefinition
