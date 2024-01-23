package com.example.dikshatest.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dikshatest.data.remote.apiservice.ErrorResult
import com.example.dikshatest.data.remote.apiservice.SuccessResult
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel(){

    private val _allMovies = MutableLiveData<List<MovieModel>>()
    val allMovies: LiveData<List<MovieModel>> get() = _allMovies
    val loadingMovies = MutableLiveData<Boolean>()
    val errorLoadMovies = MutableLiveData<String>()

    var currentPage = 1


    fun getAllMovies() {
        loadingMovies.value = true
        viewModelScope.launch {
            movieRepository.popularMovies(currentPage).collect{
                when(it){
                    is ErrorResult -> {
                        loadingMovies.value = false
                        errorLoadMovies.postValue(it.message.orEmpty())

                    }
                    is SuccessResult -> {
                        loadingMovies.value = false
                        val currentMovies = _allMovies.value.orEmpty().toMutableList()
                        currentMovies.addAll(it.data.orEmpty())
                        _allMovies.value = currentMovies
                        println("current page $currentPage")
                        currentPage++


                    }
                }

            }

        }
    }

}