package ua.ilyadreamix.m3amino.component

import android.app.Activity
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.ilyadreamix.m3amino.R

class Alerts(private val activity: Activity) {
    fun alertToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    fun alertNoButtons(
        title: String,
        message: String
    ) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .show()
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
            .setPositiveButton(buttonText) { _, _ -> onClick() }
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
            .setNegativeButton(nButtonText) { _, _ -> nOnClick() }
            .setPositiveButton(pButtonText) { _, _ -> pOnClick() }
            .setCancelable(false)
            .show()
    }
}