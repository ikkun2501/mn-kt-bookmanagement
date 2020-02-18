package com.ikkun2501.bookmanagement

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("mn.kt.hello.world")
                .mainClass(Application.javaClass)
                .start()
    }
}
