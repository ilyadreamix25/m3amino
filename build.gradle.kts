buildscript {
    extra.apply {
        // UI
        set("appCompatVersion", "1.5.1")
        set("materialVersion", "1.7.0")
        set("constraintLayoutVersion", "2.1.4")
        set("recyclerViewVersion", "1.2.1")
        set("fragmentVersion", "1.5.4")
        set("navVersion", "2.5.3")
        set("glideVersion", "4.14.2")
        set("shimmerVersion", "0.5.0")
        set("swipeRefreshLayoutVersion", "1.1.0")

        // Coroutines
        set("coreKtxVersion", "1.9.0")
        set("lifecycleVersion", "2.5.1")
        set("coroCoreVersion", "1.6.4")

        // HTTP
        set("retrofitVersion", "2.9.0")
        set("okHttp3LogVersion", "4.9.3")

        // Hash
        set("commonsCodecVersion", "1.15")
        set("commonsLangVersion", "3.12.0")
    }
}

plugins {
    id("com.android.application")      version "7.3.1"  apply false
    id("com.android.library")          version "7.3.1"  apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}