@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("imaginary.android.library")
    id("imaginary.android.hilt")
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "com.developance.core.datastore"
}


dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    testImplementation(project(":core:testing"))
    testImplementation(project(":core:datastore-test"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.dataStore.preferences)
}
