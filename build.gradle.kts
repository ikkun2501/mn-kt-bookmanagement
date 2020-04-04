import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("org.jetbrains.kotlin.jvm") version Version.kotlin
    id("org.jetbrains.kotlin.kapt") version Version.kotlin
    id("org.jetbrains.kotlin.plugin.allopen") version Version.kotlin
    id("org.jlleitschuh.gradle.ktlint") version Version.ktlintPlugin
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("application")
}

allprojects {

    version = "0.0.1"
    group = "com.github.ikkun2501"

    repositories {
        mavenCentral()
        maven(url = "https://jcenter.bintray.com")
    }

    apply(from = "$rootDir/env/dataSource.gradle.kts")

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")

    ktlint {
        additionalEditorconfigFile.set(file(".editorconfig"))
    }

    val developmentOnly by configurations.creating
    configurations {
        runtimeClasspath {
            extendsFrom(developmentOnly)
        }
    }
    tasks {

        allOpen {
            annotation("io.micronaut.aop.Around")
        }

        compileKotlin {
            // kotlinのコンパイル前にjooqのコードを自動生成
            kotlinOptions {
                jvmTarget = Version.java
                javaParameters = true
                freeCompilerArgs = listOf(
                    "-Xjsr305=strict",
                    "-XXLanguage:+InlineClasses"
                )
            }
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = Version.java
        }

        test {
            useJUnitPlatform()
            outputs.upToDateWhen { false }
            testLogging {
                events("passed", "failed", "skipped")
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
}

dependencies {
    HandlerScope.implementation.let {
        it(project(":api"))
        it(project(":domain"))
        it(project(":infrastructure"))

        it(Libs.kotlinStd)
        it(Libs.kotlinReflect)

        it(platform(Libs.micronautBom))

        // DB
        it(Libs.micronautJdbcHikari)
        it(Libs.micronautJooq) {
            exclude(module = "org.jooq:jooq")
        }
        it(Libs.jooq)
        it(Libs.jooqCodegen)
        it(Libs.jooqMeta)

        it(Libs.micronautRuntime)
        it(Libs.micronautJwt)
        it(Libs.micronautHttpClient)
        it(Libs.micronautNetty)
        it(Libs.micronautInjectJava)

        it(Libs.javaxAnnotation)
        it(Libs.swaggerAnnotation)

        it(Libs.micronautSpring)
        it(Libs.mysql)
    }

    HandlerScope.runtimeOnly.let {
        it(Libs.micronautJdbcHikari)
        it(Libs.springJdbc)
        it(Libs.jacksonKotlin)
        it(Libs.logbackClassic)
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

    HandlerScope.testImplementation.let {
        it(platform(Libs.micronautBom))
        it(Libs.mockk)
        it(Libs.junitJupiterApi)
        it(Libs.micronautTestJunit5)
        it(Libs.dbsetup)
        it(Libs.dbsetupKotlin)
        it(Libs.jooq)
    }

    HandlerScope.testRuntimeOnly.let {
        it(Libs.junitJupiterEngine)
    }
}

application {
    mainClassName = "com.ikkun2501.bookmanagement.Application"
}

tasks {

    named<CreateStartScripts>("startScripts") {
        mainClassName = "com.ikkun2501.bookmanagement.Application"
    }

    named<ShadowJar>("shadowJar") {
        this.configurations
        mergeServiceFiles()
    }
}

// run.classpath += configurations.developmentOnly
// run.jvmArgs('-noverify', '-XX:TieredStopAtLevel=1', '-Dcom.sun.management.jmxremote')
