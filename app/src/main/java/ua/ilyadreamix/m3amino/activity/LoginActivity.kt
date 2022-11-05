package ua.ilyadreamix.m3amino.activity

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ua.ilyadreamix.m3amino.BuildConfig
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.component.LoadingButton
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

        val loginButton = findViewById<MaterialCardView>(R.id.login_button)
        loginButton.setOnClickListener {
            showWarning()
        }
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
            if (loginResponse.state == ResponseState.BAD)
                showLoginError(loginResponse.error!!.message)

            setEnabled()
            setLoading()
        }
    }

    private fun setEnabled() {
        findViewById<TextInputLayout>(R.id.login_email_text_layout).isEnabled = !enabled
        findViewById<TextInputLayout>(R.id.login_password_text_layout).isEnabled = !enabled
        findViewById<MaterialCardView>(R.id.login_button).isEnabled = !enabled

        enabled = !enabled
    }

    private fun setLoading() {
        val button = findViewById<MaterialCardView>(R.id.login_button)
        val buttonCPI = button.findViewById<CircularProgressIndicator>(R.id.loading_button_cpi)

        val loadingButton = LoadingButton(
            buttonCPI,
            resources.getInteger(android.R.integer.config_shortAnimTime)
        )

        loadingButton.fullAnim(!enabled)
    }

    private fun showWarning() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.ad_warning_title))
            .setMessage(getString(R.string.ad_warning_message))
            .setNegativeButton(getString(R.string.ad_cancel)) { _, _ ->
                // Cancel
            }
            .setPositiveButton(getString(R.string.ad_ok)) { _, _ ->
                setEnabled()
                setLoading()
                login()
            }
            .setCancelable(false)
            .show()
    }

    private fun showLoginError(message: String) {
        val dialog = MaterialAlertDialogBuilder(this)

        dialog.setTitle(getString(R.string.ad_login_error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ad_ok)) { _, _ ->
                // Dismiss
            }
            .setCancelable(false)

        dialog.show()
    }
}