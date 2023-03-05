@file:Suppress("UnstableApiUsage")

plugins {
    id("imaginary.android.application")
    id("imaginary.android.application.compose")
    id("imaginary.android.application.jacoco")
    id("imaginary.android.hilt")
    id("jacoco")
}

android {
    defaultConfig {
        applicationId = "com.developance.imaginary"
        versionCode = 1
        versionName = "1" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        // Custom test runner to set up Hilt dependency graph
        testInstrumentationRunner = "com.developance.imaginary.core.testing.ImaginaryTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        buildConfig = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    namespace = "com.developance.imaginary"

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

}

dependencies {

    implementation(project(":feature:topics"))
    implementation(project(":feature:favorites"))
    implementation(project(":feature:search"))

    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(project(":core:datastore-test"))
    androidTestImplementation(project(":core:data-test"))
    androidTestImplementation(project(":core:network"))
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.accompanist.testharness)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.timber)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
}

// androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
configurations.configureEach {
    resolutionStrategy {
        force(libs.junit4)
        // Temporary workaround for https://issuetracker.google.com/174733673
        force("org.objenesis:objenesis:2.6")
    }
}
