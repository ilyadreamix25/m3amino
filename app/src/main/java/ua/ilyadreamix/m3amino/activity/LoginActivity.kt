package ua.ilyadreamix.m3amino.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ua.ilyadreamix.m3amino.BuildConfig
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModelModel
import ua.ilyadreamix.m3amino.http.request.AuthRequest
import ua.ilyadreamix.m3amino.http.request.BaseResponse
import ua.ilyadreamix.m3amino.http.request.ResponseState

class LoginActivity: M3AminoActivity() {

    private var enabled = true

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
                setEnabled()
                login()
            }
            .setCancelable(false)
            .show()
    }

    private fun login() {
        val emailET = findViewById<TextInputEditText>(R.id.login_email_text_field)
        val passwordET = findViewById<TextInputEditText>(R.id.login_password_text_field)

        val loginEmailLivedata: LiveData<BaseResponse<LoginEmailResponseModelModel>> = liveData {
            val response = AuthRequest(
                acceptLanguage = getString(R.string.language),
                ndcLang = getString(R.string.ndc_language)
            ).loginByEmail(
                emailET.text.toString(),
                passwordET.text.toString()
            )
            emit(response)
        }

        loginEmailLivedata.observe(this) { loginResponse ->
            if (loginResponse.state == ResponseState.BAD) {
                Toast.makeText(this, loginResponse.error!!.message, Toast.LENGTH_SHORT).show()
            } else {
                println(loginResponse.data)
                Toast.makeText(this, loginResponse.data!!.message, Toast.LENGTH_SHORT).show()
            }

            setEnabled()
        }
    }

    private fun setEnabled() {
        findViewById<TextInputLayout>(R.id.login_email_text_layout).isEnabled = !enabled
        findViewById<TextInputLayout>(R.id.login_password_text_layout).isEnabled = !enabled
        findViewById<Button>(R.id.login_button).isEnabled = !enabled

        enabled = !enabled
    }
}