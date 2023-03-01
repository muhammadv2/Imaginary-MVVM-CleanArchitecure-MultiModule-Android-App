
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("imaginary.android.library")
    id("imaginary.android.hilt")
    alias(libs.plugins.ksp)
}

android {
    defaultConfig {

        testInstrumentationRunner = "com.developance.testing.ImaginaryTestRunner"
    }
    namespace = "com.google.samples.apps.nowinandroid.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    androidTestImplementation(project(":core:testing"))
}