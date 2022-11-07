package ua.ilyadreamix.m3amino.http.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ua.ilyadreamix.m3amino.http.model.CommunitiesResponseModel

interface CommunityService {
    @GET("g/s/community/joined")
    suspend fun getAccountCommunities(
        @Query("v") v: Int = 1,
        @Query("start") start: Int = 0,
        @Query("start") size: Int = 25,
        @Header("NDCAUTH") ndcAuth: String,
        @Header("NDCLANG") ndcLang: String,
        @Header("NDCDEVICEID") ndcDeviceId: String,
        @Header("User-Agent") userAgent: String,
        @Header("Accept-Language") acceptLanguage: String,
    ): Response<CommunitiesResponseModel>
}