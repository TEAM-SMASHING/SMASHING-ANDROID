import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.smashing.app"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.smashing.app"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KAKAO_API_KEY", properties.getProperty("kakao.api.key"))
        buildConfigField("String", "KAKAO_BASE_URL", properties.getProperty("kakao.base.url"))
        buildConfigField("String", "KAKAO_APP_KEY", "\"${properties.getProperty("kakao.app.key")}\"")

        manifestPlaceholders["KAKAO_APP_KEY"] = properties.getProperty("kakao.app.key")
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("${project.rootDir.absolutePath}/keystore/smashing-debug-key.jks")
            storePassword = properties.getProperty("debug.store.password")
            keyAlias = properties.getProperty("debug.key.alias")
            keyPassword = properties.getProperty("debug.key.password")
        }

        create("release") {
            storeFile = file("${project.rootDir.absolutePath}/keystore/smashing-release-key.jks")
            storePassword = properties.getProperty("release.store.password")
            keyAlias = properties.getProperty("release.key.alias")
            keyPassword = properties.getProperty("release.key.password")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            buildConfigField("String", "BASE_URL", properties.getProperty("dev.base.url"))
            signingConfig = signingConfigs.getByName("debug")
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", properties.getProperty("prod.base.url"))
            signingConfig = signingConfigs.getByName("release")
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

    // Kakao
    implementation(libs.kakao.sdk.user)

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
