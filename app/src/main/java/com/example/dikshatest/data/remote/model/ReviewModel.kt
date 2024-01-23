package com.example.dikshatest.data.remote.model

import com.google.gson.annotations.SerializedName

data class ReviewModel(

    @SerializedName("author")
    val author: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("created_at")
    val createdAt: String


)
