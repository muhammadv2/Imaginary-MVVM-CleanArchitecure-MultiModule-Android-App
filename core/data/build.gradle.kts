@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("imaginary.android.library")
    id("imaginary.android.hilt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.developance.imaginary.core.data"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    defaultConfig{
        // Custom test runner to set up Hilt dependency graph
        testInstrumentationRunner = "com.developance.testing.ImaginaryTestRunner"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))

    androidTestImplementation(project(":core:testing"))

    testImplementation(project(":core:testing"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.room.paging)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.coil.kt)
    implementation(libs.kotlinx.datetime)
    implementation(libs.timber)

    testImplementation(libs.google.truth)
}