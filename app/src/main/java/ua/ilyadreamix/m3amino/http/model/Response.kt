package ua.ilyadreamix.m3amino.http.model

import com.google.gson.annotations.SerializedName

open class BasicResponseModel(
    @SerializedName("api:statuscode")
    val statusCode: Int = -1,
    @SerializedName("api:message")
    val message: String = "Unable to make an request"
)

data class LoginEmailResponseModelModel(
    @SerializedName("auid")
    val auid: String?,
    @SerializedName("account")
    val accountModel: AccountModel?,
    @SerializedName("secret")
    val secret: String?,
    @SerializedName("userProfile")
    val userProfileModel: UserProfileModel?,
    @SerializedName("deviceId")
    val deviceId: String?
): BasicResponseModel()