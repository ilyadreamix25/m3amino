package ua.ilyadreamix.m3amino.http.request

import ua.ilyadreamix.m3amino.http.RetrofitInstance
import ua.ilyadreamix.m3amino.http.model.CommunitiesResponseModel
import ua.ilyadreamix.m3amino.http.service.CommunityService

class CommunityRequest(
    acceptLanguage: String = "en-US",
    ndcLang: String = "EN"
): BaseRequest() {

    private val service = RetrofitInstance
        .getRetrofitInstance(acceptLanguage, ndcLang)
        .create(CommunityService::class.java)

    suspend fun getAccountCommunities(
        start: Int = 0, size: Int = 25
    ): BaseResponse<CommunitiesResponseModel> {
        val response = service.getAccountCommunities(
            start = start,
            size = size
        )

        return getFromResponse(response)
    }
}