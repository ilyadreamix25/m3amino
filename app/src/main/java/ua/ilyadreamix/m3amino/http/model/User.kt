package ua.ilyadreamix.m3amino.http.model

data class Account(
    val uid: String?,
    val aminoId: String?,
    val email: String?,
)

data class UserProfile(
    val uid: String?,
    val nickname: String?,
    val icon: String?,
    val role: Int?,
    val content: String?,
    val createdTime: Int?
)