package ua.ilyadreamix.m3amino.activity

import android.os.Build
import android.os.Bundle
import ua.ilyadreamix.m3amino.BuildConfig
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.databinding.ActivityDebugBinding

class DebugActivity : M3AminoActivity() {

    private lateinit var binding: ActivityDebugBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDebugBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        setDeviceInfo()
        setAppInfo()
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
    }
}