plugins {
    id("imaginary.android.library")
    id("imaginary.android.hilt")
}

android {
    namespace = "com.developance.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(project(":core:testing"))
}