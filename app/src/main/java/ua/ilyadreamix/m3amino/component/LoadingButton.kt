package ua.ilyadreamix.m3amino.component

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.google.android.material.progressindicator.CircularProgressIndicator

class LoadingButton(
    private val buttonCPI: CircularProgressIndicator,
    private val shortAnimationDuration: Int
) {
    fun animateCPI(enabled: Boolean = false) {
        buttonCPI.animate()
            .alpha(if (enabled) 1f else 0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    buttonCPI.visibility = if (enabled) View.VISIBLE else View.GONE
                }
            })
    }
}