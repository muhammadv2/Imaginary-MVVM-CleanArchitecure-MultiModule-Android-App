@file:Suppress("UnstableApiUsage")

plugins {
    id("imaginary.android.library")
    id("imaginary.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.google.samples.apps.nowinandroid.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))


    testImplementation(project(":core:testing"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.room.paging)
    implementation(libs.room.ktx)
    implementation(libs.coil.kt)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}