package ua.ilyadreamix.m3amino.http.request

import ua.ilyadreamix.m3amino.http.RetrofitInstance
import ua.ilyadreamix.m3amino.http.model.CommunitiesResponseModel
import ua.ilyadreamix.m3amino.http.service.CommunityService
import ua.ilyadreamix.m3amino.http.utility.AminoRequestUtility

class CommunityRequest(
    deviceId: String = AminoRequestUtility.generateDeviceId(),
    userAgent: String = System.getProperty("http.agent") as String,
    acceptLanguage: String = "en-US",
    ndcLang: String = "EN",
    ndcAuth: String? = null
): BaseRequest(deviceId, userAgent, acceptLanguage, ndcLang, ndcAuth) {

    private val service = RetrofitInstance
        .getRetrofitInstance()
        .create(CommunityService::class.java)

    suspend fun getAccountCommunities(start: Int = 0, size: Int = 25): BaseResponse<CommunitiesResponseModel> {
        val response = service.getAccountCommunities(
            start = start,
            size = size,
            ndcAuth = "sid=" + ndcAuth!!,
            ndcLang = ndcLang,
            ndcDeviceId = deviceId,
            acceptLanguage = acceptLanguage,
            userAgent = userAgent
        )

        return getFromResponse(response)
    }
}