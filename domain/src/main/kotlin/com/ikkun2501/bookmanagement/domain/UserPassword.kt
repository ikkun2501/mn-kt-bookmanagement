package com.ikkun2501.bookmanagement.domain

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

val passwordEncoder = BCryptPasswordEncoder()

data class RawPassword(val value: String) {
    fun encode(): EncodedPassword {
        return EncodedPassword(passwordEncoder.encode(value))
    }
}

data class EncodedPassword(val value: String) {
    fun matches(rawPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, this.value)
    }
}
