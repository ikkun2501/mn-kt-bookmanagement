plugins {
    // id("org.jetbrains.kotlin.jvm")
    // id("org.jetbrains.kotlin.kapt")
    // id("org.jetbrains.kotlin.plugin.allopen")
}

// val developmentOnly: Configuration by configurations.creating
// configurations {
//     runtimeClasspath {
//         extendsFrom(developmentOnly)
//     }
// }

dependencies {

    HandlerScope.implementation.let {
        it(platform(Libs.micronautBom))
        it(Libs.kotlinStd)
        it(Libs.kotlinReflect)
        it(Libs.micronautJwt)
        it(Libs.micronautRuntime)
        it(Libs.micronautValidation)
        it(Libs.micronautInjectJava)
        it(Libs.javaxAnnotation)
        it(Libs.springSecurityCrypto)
        it(Libs.micronautSpring)
    }

    HandlerScope.testImplementation.let {
        it(platform(Libs.micronautBom))
        it(Libs.mockk)
        it(Libs.junitJupiterApi)
        it(Libs.micronautTestJunit5)
        it(Libs.jooq)
        it(Libs.dbsetup)
        it(Libs.dbsetupKotlin)
    }

    HandlerScope.kapt.let {
        it(platform(Libs.micronautBom))
        it(Libs.micronautInjectJava)
        it(Libs.micronautValidation)
    }
    HandlerScope.kaptTest.let {
        it(platform(Libs.micronautBom))
        it(Libs.micronautInjectJava)
    }

    HandlerScope.runtimeOnly.let {
        it(Libs.jacksonKotlin)
        it(Libs.logbackClassic)
        it(Libs.springJdbc)
    }

    HandlerScope.testRuntimeOnly.let {
        it(Libs.junitJupiterEngine)
    }
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = Version.java
        javaParameters = true
        freeCompilerArgs = listOf(
            "-Xjsr305=strict",
            "-XXLanguage:+InlineClasses"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    outputs.upToDateWhen { false }
    testLogging {
        events("passed", "failed", "skipped")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
