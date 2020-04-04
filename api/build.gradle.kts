dependencies {
    HandlerScope.implementation.let {
        it(project(":domain"))
        it(Libs.kotlinStd)
        it(Libs.kotlinReflect)
        it(Libs.springSecurityCrypto)

        it(platform(Libs.micronautBom))
        it(Libs.micronautRuntime)
        it(Libs.micronautJwt)
        it(Libs.micronautHttpClient)
        it(Libs.micronautJdbcHikari)
        it(Libs.micronautNetty)
        it(Libs.micronautInjectJava)
        it(Libs.javaxAnnotation)
        it(Libs.swaggerAnnotation)
    }

    HandlerScope.testImplementation.let {
        it(project(":infrastructure"))
        it(platform(Libs.micronautBom))
        it(Libs.jooq)
        it(Libs.javafaker)
        it(Libs.dbsetup)
        it(Libs.dbsetupKotlin)
        it(Libs.junitJupiterApi)
        it(Libs.micronautTestJunit5)
        it(Libs.mockk)
    }

    HandlerScope.kapt.let {
        it(platform(Libs.micronautBom))
        it(Libs.micronautInjectJava)
        it(Libs.micronautValidation)
        it(Libs.micronautOpenapi)
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
}
