package ua.ilyadreamix.m3amino.component

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.google.android.material.progressindicator.CircularProgressIndicator

class LoadingButton(
    private val buttonCPI: CircularProgressIndicator,
    private val shortAnimationDuration: Int
) {

    private fun setCPIVisibility(
        visible: Boolean = false,
        onEnd: () -> Unit = {}
    ) {
        buttonCPI.animate()
            .alpha(if (visible) 1f else 0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    buttonCPI.visibility = if (visible) View.VISIBLE else View.GONE
                    onEnd()
                }
            })
    }

    fun fullAnim(enabled: Boolean = false) {
        setCPIVisibility(enabled)
    }
}