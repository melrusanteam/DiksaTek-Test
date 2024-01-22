package com.example.dikshatest.ui.all_movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dikshatest.data.remote.apiservice.ErrorResult
import com.example.dikshatest.data.remote.apiservice.SuccessResult
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel(){

    val popularMovies = MutableLiveData<List<MovieModel>>()
    val loadingMovies = MutableLiveData<Boolean>()
    val errorLoadMovies = MutableLiveData<String>()


    val apiKey = "e482f239c0b03ee604f193d5927beb63"

    fun getAllMovies() {
        loadingMovies.postValue(true)
        viewModelScope.launch {
            movieRepository.popularMovies(apiKey).collect{
                when(it){
                    is ErrorResult -> {
                        loadingMovies.postValue(false)
                        errorLoadMovies.postValue(it.message.orEmpty())
                    }
                    is SuccessResult -> {
                        loadingMovies.postValue(false)
                        popularMovies.postValue(it.data.orEmpty())
                    }
                }

            }

        }
    }

}