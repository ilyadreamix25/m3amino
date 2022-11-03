package ua.ilyadreamix.m3amino

import android.app.Application
import com.google.android.material.color.DynamicColors

class M3AminoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}