@file:Suppress("UnstableApiUsage")

plugins {
    id("imaginary.android.library")
    id("imaginary.android.hilt")
    id("kotlinx-serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.developance.imaginary.core.network"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    testImplementation(project(":core:testing"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    implementation(libs.okhttp.logging)
    implementation(libs.gson.core)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson.converter)

    testImplementation(libs.google.truth)
}
