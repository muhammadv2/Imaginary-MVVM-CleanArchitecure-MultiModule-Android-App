
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
    namespace = "com.developance.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    implementation(libs.google.truth)
    ksp(libs.room.compiler)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.turbine)

    androidTestImplementation(project(":core:testing"))
}