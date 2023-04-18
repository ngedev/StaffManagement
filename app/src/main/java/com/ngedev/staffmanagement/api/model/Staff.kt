package com.ngedev.staffmanagement.api.model

import com.google.gson.annotations.SerializedName

data class Staff(
    @SerializedName("name") var name: String,
    @SerializedName("job") var job: String,
    @SerializedName("id") val id: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("updatedAt") var updatedAt: String?,
)