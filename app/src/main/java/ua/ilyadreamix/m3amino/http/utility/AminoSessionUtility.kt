package ua.ilyadreamix.m3amino.http.utility

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object AminoSessionUtility {
    private lateinit var sp: SharedPreferences

    private const val SP_NAME = "amino_session"
    private const val SP_LAST_LOGIN = "last_login"
    private const val SP_SECRET = "secret"
    private const val SP_SESSION_ID = "session_id"
    private const val SP_DEVICE_ID = "device_id"
    private const val SP_USER_ID = "user_id"
    private const val SP_EMAIL = "email"

    const val LOGIN_STATUS_NONE = 0
    const val LOGIN_STATUS_EXPIRED = 1
    const val LOGIN_STATUS_SID = 2

    fun init(activity: Activity) {
        sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    fun getSessionData(): AminoSession {
        val lastLogin = sp.getLong(SP_LAST_LOGIN, 0)
        val secret = sp.getString(SP_SECRET, null)
        val sessionId = sp.getString(SP_SESSION_ID, null)
        val deviceId = sp.getString(SP_DEVICE_ID, null)
        val userId = sp.getString(SP_USER_ID, null)
        val email = sp.getString(SP_EMAIL, null)

        return AminoSession(lastLogin, secret, sessionId, deviceId, userId, email)
    }

    private fun checkNotRelogin(): Boolean {
        val sessionData = getSessionData()
        return sessionData.lastLogin + (24 * 60 * 60 * 1000) > System.currentTimeMillis()
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
        userId: String,
        email: String,
    ) {
        sp.edit()
            .putLong(SP_LAST_LOGIN, System.currentTimeMillis())
            .putString(SP_SECRET, secret)
            .putString(SP_SESSION_ID, sessionId)
            .putString(SP_DEVICE_ID, deviceId)
            .putString(SP_USER_ID, userId)
            .putString(SP_EMAIL, email)
            .apply()
    }

    @Suppress("unused")
    fun setLoginTime(loginTime: Long = System.currentTimeMillis()) {
        sp.edit()
            .putLong(SP_LAST_LOGIN, loginTime)
            .apply()
    }

    data class AminoSession(
        val lastLogin: Long,
        val secret: String?,
        val sessionId: String?,
        val deviceId: String?,
        val userId: String?,
        val email: String?
    )
}