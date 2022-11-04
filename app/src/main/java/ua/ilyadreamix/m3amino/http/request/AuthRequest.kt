package ua.ilyadreamix.m3amino.http.request

import com.google.gson.Gson
import ua.ilyadreamix.m3amino.http.RetrofitInstance
import ua.ilyadreamix.m3amino.http.model.BasicResponse
import ua.ilyadreamix.m3amino.http.model.LoginByEmailRequest
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponse
import ua.ilyadreamix.m3amino.http.service.LoginService

class AuthRequest(
    private val deviceId: String,
    private val userAgent: String = System.getProperty("http.agent") as String,
    private val acceptLanguage: String = "en-US",
    private val ndcLang: String = "en"
) {
    private val service = RetrofitInstance
        .getRetrofitInstance()
        .create(LoginService::class.java)

    class AuthResponse(
        val state: ResponseState,
        val error: BasicResponse?,
        val data: LoginEmailResponse?
    )

    suspend fun loginByEmail(email: String, password: String): AuthResponse {
        val response = service
            .loginByEmail(
                LoginByEmailRequest(
                    email,
                    "0 $password",
                    deviceId = deviceId
                ),
                userAgent,
                acceptLanguage,
                ndcLang,
                deviceId,
                ""
            )

        return if (!response.isSuccessful) {
            val body = response.errorBody()!!.charStream()

            AuthResponse(
                ResponseState.BAD,
                Gson().fromJson(body, BasicResponse::class.java),
                null
            )
        } else {
            AuthResponse(
                ResponseState.BAD,
                null,
                response.body()!!
            )
        }
    }
}