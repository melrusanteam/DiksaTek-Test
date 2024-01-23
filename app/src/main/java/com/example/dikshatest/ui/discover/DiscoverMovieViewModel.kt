package com.example.dikshatest.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dikshatest.data.remote.apiservice.ErrorResult
import com.example.dikshatest.data.remote.apiservice.SuccessResult
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel(){

    private val _movies = MutableLiveData<List<MovieModel>>()
    val movieData : LiveData<List<MovieModel>> get() = _movies

    val loadingMovies = MutableLiveData<Boolean>()
    val errorMovies = MutableLiveData<String>()

    lateinit var currentGenre: GenreModel

    var currentPage = 1
    fun onViewLoaded(genre: GenreModel) {
        this.currentGenre = genre
        loadMovies()
    }




    fun loadMovies() {
        loadingMovies.value = true
        viewModelScope.launch {
            movieRepository.discoverMovie(currentPage, currentGenre.id).collect{
                when(it){
                    is ErrorResult -> {
                        loadingMovies.value = false
                        errorMovies.postValue(it.message.orEmpty())

                    }
                    is SuccessResult -> {
                        loadingMovies.value = false
                        val currentMovies = _movies.value.orEmpty().toMutableList()
                        currentMovies.addAll(it.data.orEmpty())
                        _movies.value = currentMovies
                        currentPage++
                    }
                }

            }

        }
    }




}