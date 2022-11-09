package ua.ilyadreamix.m3amino.http.utility

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64

object AminoSPUtility {
    private lateinit var sp: SharedPreferences
    private lateinit var llSp: SharedPreferences

    private const val SP_NAME = "amino_session"
    private const val SP_LAST_LOGIN = "last_login"
    private const val SP_SECRET = "secret"
    private const val SP_SESSION_ID = "session_id"
    private const val SP_DEVICE_ID = "device_id"
    private const val SP_USER_ID = "user_id"
    private const val SP_EMAIL = "email"

    private const val SP_LL_NAME = "last_login"
    private const val SP_LL_COMMUNITIES_SIZE = "ll_communities_size"

    const val LOGIN_STATUS_NONE = 0
    const val LOGIN_STATUS_EXPIRED = 1
    const val LOGIN_STATUS_SID = 2

    fun init(activity: Activity) {
        sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        llSp = activity.getSharedPreferences(SP_LL_NAME, Context.MODE_PRIVATE)
    }

    fun getSessionData(): AminoSession {
        val lastLogin = sp.getLong(SP_LAST_LOGIN, 0)
        val deviceId = sp.getString(SP_DEVICE_ID, null)
        val userId = sp.getString(SP_USER_ID, null)

        val encodedEmail = sp.getString(SP_EMAIL, null)
        val encodedSecret = sp.getString(SP_SECRET, null)
        val encodedSessionId = sp.getString(SP_SESSION_ID, null)

        val dataToReturn = AminoSession(lastLogin, null, null, deviceId, userId, null)

        encodedEmail?.let {
            dataToReturn.secret = encodedSecret!!.fromB64()
            dataToReturn.email = it.fromB64()
            dataToReturn.sessionId = encodedSessionId!!.fromB64()
        }

        return dataToReturn
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

    private fun String.toBase64(): String {
        return Base64.encodeToString(this.toByteArray(), Base64.DEFAULT)
    }

    private fun String.fromB64(): String {
        return Base64.decode(this, Base64.DEFAULT).toString(charset("utf-8"))
    }

    fun saveLoginData(sessionData: AminoSession) {
        val encodedSecret = sessionData.secret!!.toBase64()
        val encodedSid = sessionData.sessionId!!.toBase64()
        val encodedEmail = sessionData.email!!.toBase64()

        sp.edit()
            .putLong(SP_LAST_LOGIN, System.currentTimeMillis())
            .putString(SP_SECRET, encodedSecret)
            .putString(SP_SESSION_ID, encodedSid)
            .putString(SP_DEVICE_ID, sessionData.deviceId)
            .putString(SP_USER_ID, sessionData.userId)
            .putString(SP_EMAIL, encodedEmail)
            .apply()
    }

    fun getLastLoginCache(): AminoLastLoginCache {
        val communitiesSize = llSp.getInt(SP_LL_COMMUNITIES_SIZE, 0)
        return AminoLastLoginCache(communitiesSize)
    }

    fun saveLastLoginCache(cache: AminoLastLoginCache) {
        llSp.edit()
            .putInt(SP_LL_COMMUNITIES_SIZE, cache.communitiesSize)
            .apply()
    }

    fun setLoginTime(loginTime: Long = System.currentTimeMillis()) {
        sp.edit()
            .putLong(SP_LAST_LOGIN, loginTime)
            .apply()
    }

    data class AminoSession(
        val lastLogin: Long = 0,
        var secret: String?,
        var sessionId: String?,
        val deviceId: String?,
        val userId: String?,
        var email: String?
    )

    data class AminoLastLoginCache(val communitiesSize: Int = 0)
}