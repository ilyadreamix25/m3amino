package ua.ilyadreamix.m3amino.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import ua.ilyadreamix.m3amino.BuildConfig
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.http.request.AuthRequest
import ua.ilyadreamix.m3amino.http.request.ResponseState

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
                login()
            }
            .setCancelable(false)
            .show()
    }

    private fun login() {
        val email = findViewById<TextInputEditText>(R.id.login_email_text_field).text.toString()
        val password = findViewById<TextInputEditText>(R.id.login_password_text_field).text.toString()

        val loginEmailResponse: LiveData<AuthRequest.AuthResponse> = liveData {
            val response = AuthRequest("").loginByEmail(email, password)
            emit(response)
        }

        loginEmailResponse.observe(this) {
            if (it.state == ResponseState.BAD) {
                Toast.makeText(this, it.error!!.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it.error!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}