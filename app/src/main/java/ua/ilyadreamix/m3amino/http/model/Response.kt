package ua.ilyadreamix.m3amino.http.model

import com.google.gson.annotations.SerializedName

open class BasicResponse(
    @SerializedName("api:statuscode")
    val statusCode: Int = -1,
    @SerializedName("api:message")
    val message: String = ""
)

data class LoginEmailResponse(
    val auid: String?,
    val account: Account?,
    val secret: String?,
    val userProfile: UserProfile?,
    val deviceId: String?
): BasicResponse()