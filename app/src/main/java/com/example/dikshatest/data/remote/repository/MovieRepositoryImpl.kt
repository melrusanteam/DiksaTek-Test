package com.example.dikshatest.data.remote.repository

import com.example.dikshatest.data.remote.apiservice.ErrorResult
import com.example.dikshatest.data.remote.apiservice.MovieService
import com.example.dikshatest.data.remote.apiservice.NetworkResult
import com.example.dikshatest.data.remote.apiservice.SuccessResult
import com.example.dikshatest.data.remote.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieService: MovieService) :
    MovieRepository {



    override suspend fun popularMovies(apiKey: String): Flow<NetworkResult<List<MovieModel>>> {
        return flow {
            try{
                val data = movieService.popularMovie(apiKey)
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
}