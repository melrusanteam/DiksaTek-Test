package com.example.dikshatest.ui.choose_genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dikshatest.data.remote.apiservice.ErrorResult
import com.example.dikshatest.data.remote.apiservice.SuccessResult
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.data.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChooseGenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){

    private val _genreData = MutableLiveData<List<GenreModel>>()
    val genreModel : LiveData<List<GenreModel>> get()= _genreData

    private val loadingData = MutableLiveData<Boolean>()
    private val errorData = MutableLiveData<String>()


    fun getListGenre() {

        loadingData.postValue(true)
        viewModelScope.launch {
            movieRepository.getGenreMovie().collect{
                when(it){
                    is ErrorResult -> {
                        loadingData.postValue(false)
                        errorData.postValue(it.message.orEmpty())
                    }
                    is SuccessResult -> {
                        loadingData.postValue(false)
                        _genreData.value = it.data.orEmpty()
                    }
                }
            }
        }



    }

}