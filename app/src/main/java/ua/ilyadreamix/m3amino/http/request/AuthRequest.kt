package ua.ilyadreamix.m3amino.http.request

import com.google.gson.Gson
import ua.ilyadreamix.m3amino.http.RetrofitInstance
import ua.ilyadreamix.m3amino.http.model.LoginByEmailRequestModel
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModel
import ua.ilyadreamix.m3amino.http.service.AuthService
import ua.ilyadreamix.m3amino.http.utility.AminoRequestUtility

class AuthRequest(
    deviceId: String = AminoRequestUtility.generateDeviceId(),
    userAgent: String = AminoRequestUtility.generateUserAgent(),
    acceptLanguage: String = "en-US",
    ndcLang: String = "EN",
    ndcAuth: String? = null
): BaseRequest(deviceId, userAgent, acceptLanguage, ndcLang, ndcAuth) {

    private val service = RetrofitInstance
        .getRetrofitInstance()
        .create(AuthService::class.java)

    /** secret = "0 {password}" */
    suspend fun loginByEmail(email: String, secret: String): BaseResponse<LoginEmailResponseModel> {
        val data = LoginByEmailRequestModel(
            email,
            secret,
            deviceId = deviceId
        )

        val response = service.loginByEmail(
            data,
            userAgent,
            acceptLanguage,
            ndcLang,
            deviceId,
            AminoRequestUtility.generateSig(Gson().toJson(data))
        )

        return getFromResponse(response)
    }
}