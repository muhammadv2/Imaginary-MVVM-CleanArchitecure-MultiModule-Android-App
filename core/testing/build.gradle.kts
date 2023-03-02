plugins {
    id("imaginary.android.library")
    id("imaginary.android.hilt")
    id("imaginary.android.library.compose")
}
android {
    namespace = "com.developance.testing"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))

    implementation(libs.room.paging)
    api(libs.junit4)
    api(libs.androidx.test.core)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.androidx.compose.ui.test)
    api(libs.hilt.android.testing)
    debugApi(libs.androidx.compose.ui.testManifest)
}