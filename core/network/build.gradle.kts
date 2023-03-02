plugins {
    id("imaginary.android.library")
    id("imaginary.android.hilt")
    id("kotlinx-serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

@Suppress("UnstableApiUsage")
android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.google.samples.apps.nowinandroid.core.network"
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
    implementation(libs.retrofit.core)
    implementation(libs.gson.core)
    implementation(libs.retrofit.gson.converter)
}
