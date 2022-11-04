package ua.ilyadreamix.m3amino.http.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ua.ilyadreamix.m3amino.http.model.LoginByEmailRequest
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponse

interface LoginService {
    @POST("g/s/auth/login")
    suspend fun loginByEmail(
        @Body body: LoginByEmailRequest,
        @Header("User-Agent") userAgent: String,
        @Header("Accept-Language") acceptLanguage: String,
        @Header("NDCLANG") ndcLang: String,
        @Header("NDCDEVICEID") ndcDeviceId: String,
        @Header("NDC-MSG-SIG") ndcMsgSig: String
    ): Response<LoginEmailResponse>
}