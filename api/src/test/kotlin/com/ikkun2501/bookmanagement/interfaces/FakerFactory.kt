package com.ikkun2501.bookmanagement.interfaces

import com.github.javafaker.Faker
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import java.util.Locale
import javax.inject.Singleton

@Factory
class FakerFactory {

    @Bean
    @Singleton
    fun build(): Faker {
        return Faker(Locale("ja_JP"))
    }
}
