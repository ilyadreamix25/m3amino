package ua.ilyadreamix.m3amino.http.request

import com.google.gson.Gson
import ua.ilyadreamix.m3amino.http.RetrofitInstance
import ua.ilyadreamix.m3amino.http.model.LoginByEmailRequestModel
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModelModel
import ua.ilyadreamix.m3amino.http.service.AuthService
import ua.ilyadreamix.m3amino.http.utility.AminoRequestUtility

class AuthRequest(
    deviceId: String = AminoRequestUtility.generateDeviceId(),
    userAgent: String = System.getProperty("http.agent") as String,
    acceptLanguage: String = "en-US",
    ndcLang: String = "EN"
): BaseRequest(deviceId, userAgent, acceptLanguage, ndcLang) {

    private val service = RetrofitInstance
        .getRetrofitInstance()
        .create(AuthService::class.java)

    suspend fun loginByEmail(email: String, password: String): BaseResponse<LoginEmailResponseModelModel> {
        val data = LoginByEmailRequestModel(
            email,
            "0 $password",
            deviceId = deviceId
        )

        val response = service
            .loginByEmail(
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