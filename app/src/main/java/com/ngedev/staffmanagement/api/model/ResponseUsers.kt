package com.ngedev.staffmanagement.api.model

import com.google.gson.annotations.SerializedName

data class ResponseUsers(
    @SerializedName("data") val data: List<User>,
)

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val first_name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("avatar") val avatar: String,
)