package ua.ilyadreamix.m3amino.component

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.ilyadreamix.m3amino.R

class Alerts(private val activity: AppCompatActivity) {
    fun alertToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    fun alertOnePositive(
        title: String,
        message: String,
        buttonText: String = activity.getString(R.string.ad_ok),
        onClick: () -> Unit = {}
    ) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText) { _, _ ->
                onClick()
            }
            .setCancelable(false)
            .show()
    }

    fun alertPositiveNegative(
        title: String,
        message: String,
        pButtonText: String = activity.getString(R.string.ad_ok),
        nButtonText: String = activity.getString(R.string.ad_cancel),
        pOnClick: () -> Unit = {},
        nOnClick: () -> Unit = {}
    ) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(nButtonText) { _, _ ->
                nOnClick()
            }
            .setPositiveButton(pButtonText) { _, _ ->
                pOnClick()
            }
            .setCancelable(false)
            .show()
    }
}