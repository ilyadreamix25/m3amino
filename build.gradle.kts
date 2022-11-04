buildscript {
    extra.apply {
        set("appCompatVersion", "1.5.1")
        set("materialVersion", "1.7.0")
        set("constraintLayoutVersion", "2.1.4")
        set("coreKtxVersion", "1.9.0")
        set("lifecycleVersion", "2.5.1")
        set("coroCoreVersion", "1.6.4")
        set("retrofitVersion", "2.9.0")
        set("okHttp3LogVersion", "4.9.3")
    }
}

plugins {
    id("com.android.application")      version "7.3.1"  apply false
    id("com.android.library")          version "7.3.1"  apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}