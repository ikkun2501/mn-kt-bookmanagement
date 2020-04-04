plugins {
    // id("org.jetbrains.kotlin.jvm")
}

dependencies {
    HandlerScope.implementation.let {
        it(Libs.jooqCodegen)
        it(Libs.jooqMeta)
    }
}
