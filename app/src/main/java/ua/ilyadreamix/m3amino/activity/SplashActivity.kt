package ua.ilyadreamix.m3amino.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import ua.ilyadreamix.m3amino.databinding.ActivitySplashBinding
import ua.ilyadreamix.m3amino.http.utility.AminoSessionUtility

@SuppressLint("CustomSplashScreen")
class SplashActivity : M3AminoActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val sessionUtility = AminoSessionUtility(this)
        val loginStatus = sessionUtility.getLoginStatus()

        Log.d("SplashActivity", "Auth status: $loginStatus")

        when (loginStatus) {
            AminoSessionUtility.LOGIN_STATUS_NONE -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> Log.d("SplashActivity", "TODO")
        }
    }
}