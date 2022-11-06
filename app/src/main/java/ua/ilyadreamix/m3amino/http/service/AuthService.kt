package ua.ilyadreamix.m3amino.http.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Header
import retrofit2.http.POST
import ua.ilyadreamix.m3amino.http.model.LoginByEmailRequestModel
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModel

interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("g/s/auth/login")
    suspend fun loginByEmail(
        @Body body: LoginByEmailRequestModel,
        @Header("User-Agent") userAgent: String,
        @Header("Accept-Language") acceptLanguage: String,
        @Header("NDCLANG") ndcLang: String,
        @Header("NDCDEVICEID") ndcDeviceId: String,
        @Header("NDC-MSG-SIG") ndcMsgSig: String
    ): Response<LoginEmailResponseModel>
}