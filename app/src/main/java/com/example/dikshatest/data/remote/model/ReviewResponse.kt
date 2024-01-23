package com.example.dikshatest.data.remote.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(

    @SerializedName("id")
    val id: String,

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<ReviewModel> ?= listOf()
)
