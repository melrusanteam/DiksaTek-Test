package com.example.dikshatest.data.remote.repository

import com.example.dikshatest.data.remote.apiservice.NetworkResult
import com.example.dikshatest.data.remote.model.DetailResponse
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.model.ReviewModel
import com.example.dikshatest.data.remote.model.TrailerModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {


    suspend fun popularMovies(page: Int) : Flow<NetworkResult<List<MovieModel>>>

    suspend fun discoverMovie(page: Int, genreId: Int) : Flow<NetworkResult<List<MovieModel>>>

    suspend fun detailMovie(movieId: String) : Flow<NetworkResult<DetailResponse>>

    suspend fun reviewMovie(movieId: String, page: Int) : Flow<NetworkResult<List<ReviewModel>>>

    suspend fun trailerMovie(movieId: String) : Flow<NetworkResult<List<TrailerModel>>>

    suspend fun getGenreMovie(): Flow<NetworkResult<List<GenreModel>>>


}