package ua.ilyadreamix.m3amino.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.ilyadreamix.m3amino.BuildConfig
import ua.ilyadreamix.m3amino.R

class LoginActivity: M3AminoActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val versionTextView = findViewById<TextView>(R.id.login_version)
        versionTextView.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            showWarning()
        }
    }

    private fun showWarning() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.ad_warning_title))
            .setMessage(getString(R.string.ad_warning_message))
            .setNegativeButton(getString(R.string.ad_cancel)) { _, _ ->
                // TODO
            }
            .setPositiveButton(getString(R.string.ad_ok)) { _, _ ->
                // TODO
            }
            .setCancelable(false)
            .show()
    }
}