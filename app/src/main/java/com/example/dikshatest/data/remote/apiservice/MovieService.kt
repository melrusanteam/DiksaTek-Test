package com.example.dikshatest.data.remote.apiservice

import com.example.dikshatest.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {


    @GET(MoviePath.popularMovie)
    suspend fun popularMovie(
        @Query("api_key") apiKey : String
    ): Response<MovieResponse>





}