package ua.ilyadreamix.m3amino.http.model

import com.google.gson.annotations.SerializedName

data class Community(
    @SerializedName("agent")
    val agent: Agent?,
    @SerializedName("communityHeat")
    val communityHeat: Double?,
    @SerializedName("createdTime")
    val createdTime: String?,
    @SerializedName("endpoint")
    val endpoint: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("joinType")
    val joinType: Int?,
    @SerializedName("launchPage")
    val launchPage: LaunchPage?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("listedStatus")
    val listedStatus: Int?,
    @SerializedName("membersCount")
    val membersCount: Int?,
    @SerializedName("modifiedTime")
    val modifiedTime: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ndcId")
    val ndcId: Int?,
    @SerializedName("primaryLanguage")
    val primaryLanguage: String?,
    @SerializedName("probationStatus")
    val probationStatus: Int?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("strategyInfo")
    val strategyInfo: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("templateId")
    val templateId: Int?,
    @SerializedName("themePack")
    val themePack: ThemePack?,
    @SerializedName("updatedTime")
    val updatedTime: String?,
    @SerializedName("userAddedTopicList")
    val userAddedTopicList: String?
)

data class Agent(
    @SerializedName("accountMembershipStatus")
    val accountMembershipStatus: Int?,
    @SerializedName("followingStatus")
    val followingStatus: Int?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("isGlobal")
    val isGlobal: Boolean?,
    @SerializedName("isNicknameVerified")
    val isNicknameVerified: Boolean?,
    @SerializedName("level")
    val level: Int?,
    @SerializedName("membersCount")
    val membersCount: Int?,
    @SerializedName("membershipStatus")
    val membershipStatus: Int?,
    @SerializedName("ndcId")
    val ndcId: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("reputation")
    val reputation: Int?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("uid")
    val uid: String?
)

data class LaunchPage(
    @SerializedName("title")
    val title: String?
)

data class ThemePack(
    @SerializedName("themeColor")
    val themeColor: String?,
    @SerializedName("themePackHash")
    val themePackHash: String?,
    @SerializedName("themePackRevision")
    val themePackRevision: Int?,
    @SerializedName("themePackUrl")
    val themePackUrl: String?
)