package ua.ilyadreamix.m3amino.activity

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import ua.ilyadreamix.m3amino.BuildConfig
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.databinding.ActivityDebugBinding
import ua.ilyadreamix.m3amino.http.utility.AminoRequestUtility
import ua.ilyadreamix.m3amino.http.utility.AminoSessionUtility


class DebugActivity : M3AminoActivity() {

    private lateinit var binding: ActivityDebugBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDebugBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        setDeviceInfo()
        setAppInfo()
        setAminoInfo()
    }

    private fun setAminoInfo() {
        val sessionUtility = AminoSessionUtility(this)
        val sessionData = sessionUtility.getSessionData()

        setProps(binding.debugUa, AminoRequestUtility.generateUserAgent())
        setProps(binding.debugSecret, sessionData.lastLogin.toString())

        sessionData.userId?.let { setProps(binding.debugUserId, it) }
        sessionData.deviceId?.let { setProps(binding.debugDeviceId, it) }
        sessionData.sessionId?.let { setProps(binding.debugSid, it) }
        sessionData.secret?.let { setProps(binding.debugSecret, it) }
    }


    private fun setProps(view: TextView, text: String) {
        view.text = text
        view.setOnLongClickListener {
            copyText(text)
            true
        }
    }

    private fun copyText(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText(null, text)
        clipboard.setPrimaryClip(clip)
    }

    private fun setDeviceInfo() {
        binding.debugBrand.text = Build.BRAND
        binding.debugBoard.text = Build.BOARD
        binding.debugManufacturer.text = Build.MANUFACTURER
        binding.debugModel.text = Build.MODEL
        binding.debugProduct.text = Build.PRODUCT
    }

    private fun setAppInfo() {
        binding.debugIsDebug.text = if (BuildConfig.DEBUG) getString(R.string.yes)
                                    else getString(R.string.no)
        binding.debugPackage.text = BuildConfig.APPLICATION_ID
        binding.debugVersion.text = getString(R.string.debug_version_text,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
        binding.debugAuthStatus.text = AminoSessionUtility(this).getLoginStatus().toString()
    }
}