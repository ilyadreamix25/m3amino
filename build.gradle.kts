buildscript {
    extra.apply {
        set("appCompatVersion", "1.5.1")
        set("materialVersion", "1.7.0")
        set("constraintLayoutVersion", "2.1.4")
        set("coreKtxVersion", "1.9.0")
        set("lifecycleRuntimeKtxVersion", "2.5.1")
    }
}

plugins {
    id("com.android.application")      version "7.3.1"  apply false
    id("com.android.library")          version "7.3.1"  apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}