package com.ngedev.staffmanagement.api.model

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("token") val token: String,
    @SerializedName("error") val error: String
)