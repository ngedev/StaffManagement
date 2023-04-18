package com.ngedev.staffmanagement.api.model

import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("id") val id: String,
    @SerializedName("token") val token: String,
    @SerializedName("error") val error: String
)