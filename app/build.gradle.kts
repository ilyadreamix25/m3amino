plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ua.ilyadreamix.m3amino"
    compileSdk = 33

    defaultConfig {
        minSdk    = 21
        targetSdk = 33
        applicationId = "ua.ilyadreamix.m3amino"
        versionCode = 1
        versionName = "1.0"
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

val appCompatVersion: String by rootProject.extra
val materialVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val coreKtxVersion: String by rootProject.extra

dependencies {
    implementation("androidx.appcompat:appcompat:${appCompatVersion}")
    implementation("androidx.constraintlayout:constraintlayout:${constraintLayoutVersion}")
    implementation("androidx.core:core-ktx:${coreKtxVersion}")
    implementation("com.google.android.material:material:${materialVersion}")
}