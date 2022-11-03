package ua.ilyadreamix.m3amino.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

open class M3AminoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            ViewCompat.setOnApplyWindowInsetsListener(window.decorView.rootView) { view, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
                view.updatePadding(0, insets.top, 0, insets.bottom)
                WindowInsetsCompat.CONSUMED
            }
        }
    }
}