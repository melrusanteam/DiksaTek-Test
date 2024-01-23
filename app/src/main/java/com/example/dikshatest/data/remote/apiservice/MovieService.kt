package com.example.dikshatest.data.remote.apiservice

import com.example.dikshatest.data.remote.model.DetailResponse
import com.example.dikshatest.data.remote.model.MovieResponse
import com.example.dikshatest.data.remote.model.ReviewResponse
import com.example.dikshatest.data.remote.model.TrailerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET(MoviePath.popularMovie)
    suspend fun popularMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey : String
    ): Response<MovieResponse>



    @GET(MoviePath.detailMovie)
    suspend fun detailMovie(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): Response<DetailResponse>


    @GET(MoviePath.reviewMovie)
    suspend fun reviewMovie(
        @Path("movieId") movieId: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<ReviewResponse>

    @GET(MoviePath.trailerMovie)
    suspend fun trailerMovie(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): Response<TrailerResponse>



}