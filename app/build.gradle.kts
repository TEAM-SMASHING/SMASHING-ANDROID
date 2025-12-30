import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.smashing.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.smashing.app"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Androidx
    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.datastore.preferences)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    // Kotlinx
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.immutable)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    // Network
    implementation(libs.bundles.network)

    // DI
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    // Debug
    debugImplementation(libs.bundles.debug)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.timber)
    implementation(libs.lottie.compose)

}
