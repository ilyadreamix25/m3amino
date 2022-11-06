package ua.ilyadreamix.m3amino.http.utility

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class AminoSessionUtility(
    private val activity: AppCompatActivity
) {
    fun getSessionData(): AminoSession {
        val sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

        val lastLogin = sp.getLong(SP_LAST_LOGIN, 0)
        val secret = sp.getString(SP_SECRET, null)
        val sessionId = sp.getString(SP_SESSION_ID, null)
        val deviceId = sp.getString(SP_DEVICE_ID, null)
        val userId = sp.getString(SP_USER_ID, null)

        return AminoSession(lastLogin, secret, sessionId, deviceId, userId)
    }

    private fun checkNotRelogin(): Boolean {
        val sessionData = getSessionData()
        return sessionData.lastLogin + (12 * 60 * 60 * 1000) > System.currentTimeMillis()
    }

    fun getLoginStatus(): Int {
        val sp = getSessionData()
        val lastLogin = sp.lastLogin

        return if (lastLogin == 0L)
            LOGIN_STATUS_NONE
        else if (!checkNotRelogin())
            LOGIN_STATUS_EXPIRED
        else
            LOGIN_STATUS_SID
    }

    fun saveLoginData(
        secret: String,
        sessionId: String,
        deviceId: String,
        userId: String
    ) {
        val sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

        sp.edit()
            .putLong(SP_LAST_LOGIN, System.currentTimeMillis())
            .putString(SP_SECRET, secret)
            .putString(SP_SESSION_ID, sessionId)
            .putString(SP_DEVICE_ID, deviceId)
            .putString(SP_USER_ID, userId)
            .apply()
    }

    fun setLoginTime(loginTime: Long) {
        val sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

        sp.edit()
            .putLong(SP_LAST_LOGIN, loginTime)
            .apply()
    }

    data class AminoSession(
        val lastLogin: Long,
        val secret: String?,
        val sessionId: String?,
        val deviceId: String?,
        val userId: String?
    )

    companion object {
        const val SP_NAME = "amino_session"
        const val SP_LAST_LOGIN = "last_login"
        const val SP_SECRET = "secret"
        const val SP_SESSION_ID = "session_id"
        const val SP_DEVICE_ID = "device_id"
        const val SP_USER_ID = "user_id"

        const val LOGIN_STATUS_NONE = 0
        const val LOGIN_STATUS_EXPIRED = 1
        const val LOGIN_STATUS_SID = 2
    }
}