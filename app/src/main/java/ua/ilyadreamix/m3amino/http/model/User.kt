package ua.ilyadreamix.m3amino.http.model

import com.google.gson.annotations.SerializedName

data class AccountModel(
    @SerializedName("uid")
    val uid: String?,
    @SerializedName("aminoId")
    val aminoId: String?,
    @SerializedName("email")
    val email: String?,
)

data class UserProfileModel(
    @SerializedName("uid")
    val uid: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("role")
    val role: Int?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdTime")
    val createdTime: String?
)