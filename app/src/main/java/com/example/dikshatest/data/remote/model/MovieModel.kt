package com.example.dikshatest.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieModel(

    @SerializedName("title")
    val title: String,


    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("vote_average")
    val voteAverage: Double,


    @SerializedName("vote_count")
    val voteCount: Double,

    @SerializedName("id")
    val id: Int,

    @SerializedName("release_date")
    val releaseDate: String,





)