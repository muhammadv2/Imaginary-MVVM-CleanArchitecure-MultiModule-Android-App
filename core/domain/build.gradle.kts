plugins {
    id("imaginary.android.library")
    id("imaginary.android.library.jacoco")
    kotlin("kapt")
}

android {
    namespace = "com.google.samples.apps.nowinandroid.core.domain"
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))

    testImplementation(project(":core:testing"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}