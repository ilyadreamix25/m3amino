package ua.ilyadreamix.m3amino.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bumptech.glide.Glide
import ua.ilyadreamix.m3amino.BuildConfig
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.component.Alerts
import ua.ilyadreamix.m3amino.component.LoadingButton
import ua.ilyadreamix.m3amino.databinding.ActivityLoginBinding
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModel
import ua.ilyadreamix.m3amino.http.request.AuthRequest
import ua.ilyadreamix.m3amino.http.request.BaseResponse
import ua.ilyadreamix.m3amino.http.request.ResponseState
import ua.ilyadreamix.m3amino.http.utility.AminoRequestUtility
import ua.ilyadreamix.m3amino.http.utility.AminoSessionUtility
import java.net.UnknownHostException

class LoginActivity: M3AminoActivity() {

    private var enabled = true
    private var warningAccepted = false
    private var debugMenuCounter = 0
    private lateinit var binding: ActivityLoginBinding
    private lateinit var alerts: Alerts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        initialize()
    }

    private fun initialize() {
        alerts = Alerts(this)

        binding.loginVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        binding.loginButton.root.setOnClickListener {
            if (!warningAccepted) {
                alerts.alertPositiveNegative(
                    getString(R.string.ad_warning_title),
                    getString(R.string.ad_warning_message),
                    pOnClick = {
                        setEnabled()
                        setLoadingButton()
                        login()
                    }
                )
                warningAccepted = true
            } else {
                setEnabled()
                setLoadingButton()
                login()
            }
        }

        binding.loginBottomLayout.setOnClickListener {
            if (debugMenuCounter < 5) debugMenuCounter++
            else {
                debugMenuCounter = 0

                alerts.alertPositiveNegative(
                    getString(R.string.ad_warning_title),
                    getString(R.string.ad_warning_debug),
                    pOnClick = {
                        val intent = Intent(this, DebugActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    private fun login() {
        val emailET = binding.loginEmailTextField
        val passwordET = binding.loginPasswordTextField
        val deviceId = AminoRequestUtility.generateDeviceId()

        val loginEmailLiveData: LiveData<BaseResponse<LoginEmailResponseModel>> = liveData {
            val requester = AuthRequest(
                deviceId = deviceId,
                acceptLanguage = getString(R.string.language),
                ndcLang = getString(R.string.ndc_language)
            )

            try {
                val response = requester.loginByEmail(
                    emailET.text.toString(),
                    "0 " + passwordET.text.toString()
                )
                emit(response)
            } catch (_: UnknownHostException) {
                setEnabled()
                setLoadingButton()

                alerts.alertToast(getString(R.string.check_internet))
            } catch (error: Throwable) {
                setEnabled()
                setLoadingButton()

                Log.e("LoginActivity", "Unexpected login request error", error)

                alerts.alertToast(getString(R.string.unexpected_error))
            }
        }

        loginEmailLiveData.observe(this) {
            if (it.state == ResponseState.BAD)
                if (it.error!!.statusCode != 270)
                    alerts.alertOnePositive(
                        getString(R.string.ad_login_error),
                        it.error.message
                    )
                else {
                    alerts.alertOnePositive(
                        getString(R.string.ad_login_error),
                        it.error.message,
                        buttonText = getString(R.string.verify),
                        onClick = {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.error.url!!))
                            startActivity(browserIntent)
                        }
                    )
                }
            else {
                val sessionUtility = AminoSessionUtility(this)
                sessionUtility.saveLoginData(
                    it.data!!.secret,
                    it.data.sid,
                    deviceId,
                    it.data.userProfile.uid!!
                )

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
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
}