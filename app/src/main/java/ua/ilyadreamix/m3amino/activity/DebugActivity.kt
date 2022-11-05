package ua.ilyadreamix.m3amino.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ua.ilyadreamix.m3amino.databinding.ActivityDebugBinding

class DebugActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDebugBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDebugBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        setDeviceInfo()
    }

    private fun setDeviceInfo() {
        binding.debugBrand.text = Build.BRAND
        binding.debugBoard.text = Build.BOARD
        binding.debugManufacturer.text = Build.MANUFACTURER
        binding.debugModel.text = Build.MODEL
        binding.debugProduct.text = Build.PRODUCT
    }
}