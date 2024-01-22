package com.example.dikshatest.data.remote.repository

import com.example.dikshatest.data.remote.apiservice.NetworkResult
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {


    suspend fun popularMovies(apiKey: String) : Flow<NetworkResult<List<MovieModel>>>

}