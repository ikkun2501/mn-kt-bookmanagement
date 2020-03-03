package com.ikkun2501.bookmanagement

import io.micronaut.runtime.Micronaut

// @OpenAPIDefinition(
//     info = Info(
//         title = "mn-kotlin-openapi-app",
//         version = "1.0",
//         description = "書籍管理API"
//     )
// )
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.ikkun2501.bookmanagement")
                .mainClass(Application.javaClass)
                .start()
    }
}
