package ua.ilyadreamix.m3amino.http.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ua.ilyadreamix.m3amino.http.model.LoginByEmailRequestModel
import ua.ilyadreamix.m3amino.http.model.LoginEmailResponseModel

interface AuthService {
    @POST("g/s/auth/login")
    suspend fun loginByEmail(
        @Body body: LoginByEmailRequestModel,
        @Header("NDCDEVICEID") deviceId: String
    ): Response<LoginEmailResponseModel>
}