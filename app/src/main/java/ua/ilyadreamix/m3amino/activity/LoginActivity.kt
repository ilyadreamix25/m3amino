package ua.ilyadreamix.m3amino.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.ilyadreamix.m3amino.BuildConfig
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.component.LoadingButton
import ua.ilyadreamix.m3amino.databinding.ActivityLoginBinding
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModelModel
import ua.ilyadreamix.m3amino.http.request.AuthRequest
import ua.ilyadreamix.m3amino.http.request.BaseResponse
import ua.ilyadreamix.m3amino.http.request.ResponseState
import ua.ilyadreamix.m3amino.http.utility.AminoRequestUtility
import ua.ilyadreamix.m3amino.http.utility.AminoSessionUtility

class LoginActivity: M3AminoActivity() {

    private var enabled = true
    private var debugMenuCounter = 0
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        initialize()
    }

    private fun initialize() {
        binding.loginVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        binding.loginButton.root.setOnClickListener {
            showWarning()
        }

        binding.loginBottomLayout.setOnClickListener {
            if (debugMenuCounter < 5) debugMenuCounter++
            else {
                debugMenuCounter = 0

                val intent = Intent(this, DebugActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun login() {
        val emailET = binding.loginEmailTextField
        val passwordET = binding.loginPasswordTextField
        val deviceId = AminoRequestUtility.generateDeviceId()

        val loginEmailLivedata: LiveData<BaseResponse<LoginEmailResponseModelModel>> = liveData {
            val response = AuthRequest(
                deviceId = deviceId,
                acceptLanguage = getString(R.string.language),
                ndcLang = getString(R.string.ndc_language)
            ).loginByEmail(
                emailET.text.toString(),
                "0 " + passwordET.text.toString()
            )
            emit(response)
        }

        loginEmailLivedata.observe(this) {
            if (it.state == ResponseState.BAD)
                showLoginError(it.error!!.message)
            else {
                val sessionUtility = AminoSessionUtility(this)
                sessionUtility.saveLoginData(
                    it.data!!.secret,
                    it.data.sid,
                    deviceId,
                    it.data.userProfile.uid!!
                )
            }

            setEnabled()
            setLoadingButton()
        }
    }

    private fun setEnabled() {
        binding.loginEmailTextLayout.isEnabled = !enabled
        binding.loginPasswordTextLayout.isEnabled = !enabled
        binding.loginButton.root.isEnabled = !enabled

        enabled = !enabled
    }

    private fun setLoadingButton() {
        val loadingButton = LoadingButton(
            binding.loginButton.loadingButtonCpi,
            resources.getInteger(android.R.integer.config_shortAnimTime)
        )

        loadingButton.animateCPI(!enabled)
    }

    private fun showWarning() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.ad_warning_title))
            .setMessage(getString(R.string.ad_warning_message))
            .setNegativeButton(getString(R.string.ad_cancel)) { _, _ ->
                // Dismiss
            }
            .setPositiveButton(getString(R.string.ad_ok)) { _, _ ->
                setEnabled()
                setLoadingButton()
                login()
            }
            .setCancelable(false)
            .show()
    }

    private fun showLoginError(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.ad_login_error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ad_ok)) { _, _ ->
                // Dismiss
            }
            .setCancelable(false)
            .show()
    }
}