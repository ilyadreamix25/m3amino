package ua.ilyadreamix.m3amino.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.databinding.ActivitySplashBinding
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModel
import ua.ilyadreamix.m3amino.http.request.AuthRequest
import ua.ilyadreamix.m3amino.http.request.BaseResponse
import ua.ilyadreamix.m3amino.http.request.ResponseState
import ua.ilyadreamix.m3amino.http.utility.AminoSPUtility
import java.net.UnknownHostException

@SuppressLint("CustomSplashScreen")
class SplashActivity : M3AminoActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AminoSPUtility.init(this)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.splashShimmer.startShimmer()

        val loginStatus = AminoSPUtility.getLoginStatus()
        val data = AminoSPUtility.getSessionData()

        Log.d("SplashActivity", "Auth status: $loginStatus")

        when (loginStatus) {
            AminoSPUtility.LOGIN_STATUS_EXPIRED -> {
                val loginEmailLiveData: LiveData<BaseResponse<LoginEmailResponseModel>> = liveData {
                    val requester = AuthRequest(
                        acceptLanguage = getString(R.string.language),
                        ndcLang = getString(R.string.ndc_language)
                    )


                    try {
                        val response = requester.loginByEmail(
                            data.email!!,
                            data.secret!!
                        )

                        emit(response)
                    } catch (_: UnknownHostException) {
                        Toast.makeText(
                            this@SplashActivity,
                            getString(R.string.check_internet),
                            Toast.LENGTH_SHORT
                        ).show()

                        startLogin()
                    } catch (error: Exception) {
                        Log.e("SplashActivity", "Login data: $data")
                        Log.e("SplashActivity", "Auto-login error", error)

                        Toast.makeText(
                            this@SplashActivity,
                            getString(R.string.unexpected_error),
                            Toast.LENGTH_SHORT
                        ).show()

                        startLogin()
                    }
                }

                loginEmailLiveData.observe(this) {
                    if (it.state == ResponseState.BAD) {
                        Toast.makeText(this, it.error!!.message, Toast.LENGTH_SHORT).show()
                        startLogin()
                    } else {
                        val loginData = AminoSPUtility.AminoSession(
                            secret = data.secret,
                            sessionId = it.data!!.sid,
                            deviceId = data.deviceId,
                            userId = it.data.userProfile.uid,
                            email = data.email
                        )
                        AminoSPUtility.saveLoginData(loginData)

                        startHome()
                    }
                }
            }
            AminoSPUtility.LOGIN_STATUS_NONE -> { startLogin() }
            AminoSPUtility.LOGIN_STATUS_SID -> { startHome() }
        }
    }

    private fun startHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}