plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "ua.ilyadreamix.m3amino"
    compileSdk = 33

    defaultConfig {
        minSdk = 22
        targetSdk = 33
        applicationId = "ua.ilyadreamix.m3amino"
        versionCode = 3
        versionName = "1.06"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

/** Dependencies **/

// UI
val appCompatVersion: String by rootProject.extra
val materialVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val recyclerViewVersion: String by rootProject.extra

// Coroutines
val coreKtxVersion: String by rootProject.extra
val lifecycleVersion: String by rootProject.extra
val coroCoreVersion: String by rootProject.extra

// HTTP
val retrofitVersion: String by rootProject.extra
val okHttp3LogVersion: String by rootProject.extra

// Hash
val commonsCodecVersion: String by rootProject.extra
val commonsLangVersion: String by rootProject.extra

dependencies {
    // UI
    implementation("androidx.appcompat:appcompat:${appCompatVersion}")
    implementation("androidx.constraintlayout:constraintlayout:${constraintLayoutVersion}")
    implementation("androidx.core:core-ktx:${coreKtxVersion}")
    implementation("com.google.android.material:material:${materialVersion}")
    implementation("androidx.recyclerview:recyclerview:${recyclerViewVersion}")

    // Live data and view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${lifecycleVersion}")
    kapt("androidx.lifecycle:lifecycle-compiler:${lifecycleVersion}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroCoreVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroCoreVersion}")

    // Retrofit and OkHTTP3
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:${okHttp3LogVersion}")

    // Hash utilities
    implementation("commons-codec:commons-codec:${commonsCodecVersion}")
    implementation("org.apache.commons:commons-lang3:${commonsLangVersion}")
}