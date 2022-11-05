package ua.ilyadreamix.m3amino.http.model

import com.google.gson.annotations.SerializedName

data class LoginByEmailRequestModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("secret")
    val secret: String? = null,
    @SerializedName("clientType")
    val clientType: Int = 100,
    @SerializedName("deviceID")
    val deviceId: String,
    @SerializedName("v")
    val v: Int = 2,
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)