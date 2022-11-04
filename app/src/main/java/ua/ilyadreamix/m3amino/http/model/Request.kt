package ua.ilyadreamix.m3amino.http.model

import com.google.gson.annotations.SerializedName

data class LoginByEmailRequest(
    val email: String,
    val secret: String? = null,
    val clientType: Int = 100,
    @SerializedName("deviceID")
    val deviceId: String,
    val v: Int = 2
)