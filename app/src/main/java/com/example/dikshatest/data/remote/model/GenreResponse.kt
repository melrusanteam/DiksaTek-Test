package com.example.dikshatest.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(

    @SerializedName("genres")
    val genres: List<GenreModel> = listOf()

)