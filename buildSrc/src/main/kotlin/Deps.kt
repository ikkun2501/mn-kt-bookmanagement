object Version {
    // lib
    const val java = "11"
    const val kotlin = "1.3.70"
    const val micronaut = "1.3.2"
    const val spring = "5.2.1.RELEASE"
    const val jooq = "3.13.1"
    const val dbsetup = "2.1.0"
    const val javafaker = "1.0.2"
    const val logback = "1.2.3"
    const val slf4j = "1.7.25"
    const val mockk = "1.9.3"
    const val jacksonKotlin = "2.9.9"
    const val flyway = "6.2.4"
    const val mysql = "8.0.18"

    // plugin
    const val flywayPlugin = "6.2.4"
    const val jooqPlugin = "4.1"
    const val ktlintPlugin = "9.2.1"
}

object Libs {

    // Kotlin
    const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}"

    // spring
    const val springSecurityCrypto =
        "org.springframework.security:spring-security-crypto:${Version.spring}"
    const val springJdbc = "org.springframework:spring-jdbc:${Version.spring}"

    // jooq
    const val jooq = "org.jooq:jooq:${Version.jooq}"
    const val jooqCodegen = "org.jooq:jooq-codegen:${Version.jooq}"
    const val jooqMeta = "org.jooq:jooq-meta:${Version.jooq}"

    // mysql
    const val mysql = "mysql:mysql-connector-java:${Version.mysql}"

    // micronaut
    const val micronautBom = "io.micronaut:micronaut-bom:${Version.micronaut}"
    const val micronautRuntime = "io.micronaut:micronaut-runtime"
    const val micronautJwt = "io.micronaut:micronaut-security-jwt"
    const val micronautHttpClient = "io.micronaut:micronaut-http-client"
    const val micronautJdbcHikari = "io.micronaut.configuration:micronaut-jdbc-hikari"
    const val micronautNetty = "io.micronaut:micronaut-http-server-netty"
    const val micronautInjectJava = "io.micronaut:micronaut-inject-java"
    const val micronautValidation = "io.micronaut:micronaut-validation"
    const val micronautOpenapi = "io.micronaut.configuration:micronaut-openapi"
    const val micronautSpring = "io.micronaut:micronaut-spring"
    const val micronautJooq = "io.micronaut.configuration:micronaut-jooq"

    const val javaxAnnotation = "javax.annotation:javax.annotation-api"
    const val swaggerAnnotation = "io.swagger.core.v3:swagger-annotations"

    // dbsetup
    const val dbsetup = "com.ninja-squad:DbSetup:${Version.dbsetup}"
    const val dbsetupKotlin = "com.ninja-squad:DbSetup-kotlin:${Version.dbsetup}"

    // javafaker
    const val javafaker = "com.github.javafaker:javafaker:${Version.javafaker}"

    const val jacksonKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Version.jacksonKotlin}"

    const val logbackClassic = "ch.qos.logback:logback-classic:${Version.logback}"
    const val slf4jSimple = "org.slf4j:slf4j-simple:${Version.slf4j}"

    const val mockk = "io.mockk:mockk:${Version.mockk}"
    const val junitJupiterApi = "org.junit.jupiter:junit-jupiter-api"
    const val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine"
    const val micronautTestJunit5 = "io.micronaut.test:micronaut-test-junit5"
}

object HandlerScope {
    const val implementation = "implementation"
    const val testImplementation = "testImplementation"
    const val kapt = "kapt"
    const val kaptTest = "kaptTest"
    const val runtimeOnly = "runtimeOnly"
    const val testRuntimeOnly = "testRuntimeOnly"
    const val jooqRuntime = "jooqRuntime"
}
