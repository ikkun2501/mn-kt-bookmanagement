plugins {
    id("nu.studer.jooq") version Version.jooqPlugin
    id("org.flywaydb.flyway") version Version.flywayPlugin
}

dependencies {
    HandlerScope.implementation.let {
        it(project(":domain"))

        it(Libs.kotlinStd)
        it(Libs.kotlinReflect)

        it(platform(Libs.micronautBom))
        it(Libs.micronautJdbcHikari)
        it(Libs.micronautJooq) {
            exclude(module = "org.jooq:jooq")
        }
        it(Libs.micronautInjectJava)

        it(Libs.javaxAnnotation)
        it(Libs.springSecurityCrypto)

        it(Libs.jooq)
        it(Libs.jooqCodegen)
        it(Libs.jooqMeta)

        it(Libs.mysql)
    }

    HandlerScope.testImplementation.let {
        it(platform(Libs.micronautBom))
        it(Libs.mockk)
        it(Libs.junitJupiterApi)
        it(Libs.micronautTestJunit5)
        it(Libs.dbsetup)
        it(Libs.dbsetupKotlin)
    }

    HandlerScope.kapt.let {
        it(platform(Libs.micronautBom))
        it(Libs.micronautInjectJava)
    }

    HandlerScope.kaptTest.let {
        it(platform(Libs.micronautBom))
        it(Libs.micronautInjectJava)
    }

    HandlerScope.runtimeOnly.let {
        it(Libs.jacksonKotlin)
        it(Libs.logbackClassic)
    }

    HandlerScope.testRuntimeOnly.let {
        it(Libs.junitJupiterEngine)
    }

    HandlerScope.jooqRuntime.let {
        it(Libs.mysql)
        it(project(":jooq-custom-strategy"))
        it(Libs.slf4jSimple)
    }
}

val jdbcDriver = "com.mysql.cj.jdbc.Driver"
val jooqDestDir = "$buildDir/jooq-gen/"
val jooqDestPackage = "com.ikkun2501.bookmanagement.infrastructure.jooq.gen"

apply(from = "useJooq.gradle")

// ////////////////////
// flyway
// ////////////////////
// 呼び出す
apply(from = "$rootDir/env/dataSource.gradle.kts")
val datasource: Map<String, DataSource> by extra
val defaultDataSource = datasource["default"]!!
val testDataSource = datasource["test"]!!

configure<org.flywaydb.gradle.FlywayExtension> {
    url = defaultDataSource.url
    user = defaultDataSource.user
    password = defaultDataSource.password
    schemas = arrayOf("bookmanagement")
}

sourceSets {
    // jooqのsrcファイルを追加
    getByName("main").java.srcDirs("src", jooqDestDir)
}

tasks {

    clean {
        delete(jooqDestDir)
    }

    create<org.flywaydb.gradle.task.FlywayMigrateTask>("unitFlywayMigrate") {
        url = testDataSource.url
        user = testDataSource.user
        password = testDataSource.password
        schemas = arrayOf("bookmanagement")
    }

    create<org.flywaydb.gradle.task.FlywayCleanTask>("unitFlywayClean") {
        url = testDataSource.url
        user = testDataSource.user
        password = testDataSource.password
        schemas = arrayOf("bookmanagement")
    }
}
