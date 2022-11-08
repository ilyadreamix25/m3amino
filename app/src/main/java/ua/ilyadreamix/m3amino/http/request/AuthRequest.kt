package ua.ilyadreamix.m3amino.http.request

import ua.ilyadreamix.m3amino.http.RetrofitInstance
import ua.ilyadreamix.m3amino.http.model.LoginByEmailRequestModel
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModel
import ua.ilyadreamix.m3amino.http.service.AuthService
import ua.ilyadreamix.m3amino.http.utility.AminoRequestUtility

class AuthRequest(
    acceptLanguage: String = "en-US",
    ndcLang: String = "EN"
): BaseRequest() {

    private val service = RetrofitInstance
        .getRetrofitInstance(acceptLanguage, ndcLang)
        .create(AuthService::class.java)

    /** secret = "0 {password}" */
    suspend fun loginByEmail(email: String, secret: String): BaseResponse<LoginEmailResponseModel> {
        val deviceId = AminoRequestUtility.generateDeviceId()

        val data = LoginByEmailRequestModel(
            email,
            secret,
            deviceId = deviceId
        )

        val response = service.loginByEmail(data, deviceId)

        return getFromResponse(response)
    }
}