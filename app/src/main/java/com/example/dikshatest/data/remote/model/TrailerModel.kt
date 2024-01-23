package com.example.dikshatest.data.remote.model

import com.google.gson.annotations.SerializedName

data class TrailerModel(
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String
)