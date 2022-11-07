package ua.ilyadreamix.m3amino.http.model

import com.google.gson.annotations.SerializedName

open class BasicResponseModel(
    /** 270 - verify account */
    @SerializedName("api:statuscode")
    val statusCode: Int = -1,
    @SerializedName("api:message")
    val message: String = "Unable to make an request",
    @SerializedName("url")
    val url: String? = null
)

data class LoginEmailResponseModel(
    @SerializedName("auid")
    val auid: String,
    @SerializedName("account")
    val account: AccountModel,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("userProfile")
    val userProfile: UserProfileModel,
    @SerializedName("sid")
    val sid: String
): BasicResponseModel()

data class CommunitiesResponseModel(
    val communityList: List<Community>
): BasicResponseModel()
