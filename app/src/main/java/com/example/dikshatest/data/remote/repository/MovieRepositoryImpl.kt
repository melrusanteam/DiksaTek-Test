package com.example.dikshatest.data.remote.repository

import com.example.dikshatest.data.remote.apiservice.ErrorResult
import com.example.dikshatest.data.remote.apiservice.MovieService
import com.example.dikshatest.data.remote.apiservice.NetworkResult
import com.example.dikshatest.data.remote.apiservice.SuccessResult
import com.example.dikshatest.data.remote.model.DetailResponse
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.model.ReviewModel
import com.example.dikshatest.data.remote.model.TrailerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    @Named("api_key") private val apiKey: String
) :
    MovieRepository {



    override suspend fun popularMovies(page: Int): Flow<NetworkResult<List<MovieModel>>> {
        return flow {
            try{
                val data = movieService.popularMovie(page, apiKey)
                if(data.isSuccessful){
                    emit(SuccessResult(data.body()?.results))
                } else
                    throw Exception("Failed load movies")

            } catch (e: Exception){
                e.printStackTrace()
                emit(ErrorResult(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun discoverMovie(page: Int, genreId: Int): Flow<NetworkResult<List<MovieModel>>> {
        return flow {
            try{
                val data = movieService.discoverMovie(page, genreId,apiKey)
                if(data.isSuccessful){
                    emit(SuccessResult(data.body()?.results))
                } else
                    throw Exception("Failed load movies")

            } catch (e: Exception){
                e.printStackTrace()
                emit(ErrorResult(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun detailMovie(movieId: String): Flow<NetworkResult<DetailResponse>> {
        return flow {
            try {
                val response = movieService.detailMovie(movieId, apiKey)
                if(response.isSuccessful){
                    emit(SuccessResult(response.body()))
                } else{
                    throw Exception("Failed to load detail")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                emit(ErrorResult(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun reviewMovie(
        movieId: String,
        page: Int,
    ): Flow<NetworkResult<List<ReviewModel>>> {
        return flow {
            try {
                val response = movieService.reviewMovie(movieId, page, apiKey)
                if(response.isSuccessful){
                    emit(SuccessResult(response.body()?.results))
                } else{
                    throw Exception("Failed to load review")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ErrorResult(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun trailerMovie(
        movieId: String
    ): Flow<NetworkResult<List<TrailerModel>>> {
        return flow {
            try {
                val response = movieService.trailerMovie(movieId, apiKey)
                if(response.isSuccessful){
                    emit(SuccessResult(response.body()?.results))
                } else{
                    throw Exception("Failed to load trailer")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ErrorResult(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getGenreMovie(): Flow<NetworkResult<List<GenreModel>>> {
        return flow {
            try {
                val response = movieService.genreMovie(apiKey)
                if(response.isSuccessful){
                    emit(SuccessResult(response.body()?.genres))
                } else{
                    throw Exception("Failed to load trailer")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ErrorResult(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }
}