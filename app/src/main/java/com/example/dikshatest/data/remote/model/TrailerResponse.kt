package com.example.dikshatest.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class TrailerResponse (

    @SerializedName("id")
    val id: Int,

    @SerializedName("results")
    val results: List<TrailerModel>

)