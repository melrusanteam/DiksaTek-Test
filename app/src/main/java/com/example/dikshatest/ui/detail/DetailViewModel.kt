package com.example.dikshatest.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dikshatest.data.remote.apiservice.ErrorResult
import com.example.dikshatest.data.remote.apiservice.SuccessResult
import com.example.dikshatest.data.remote.model.DetailResponse
import com.example.dikshatest.data.remote.model.ReviewModel
import com.example.dikshatest.data.remote.model.TrailerModel
import com.example.dikshatest.data.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


    private val _detailMovieData = MutableLiveData<DetailResponse>()
    val detailMovie : LiveData<DetailResponse> get() = _detailMovieData
    val loadingData = MutableLiveData<Boolean>()
    val loadingReviewData = MutableLiveData<Boolean>()
    val loadingTrailer = MutableLiveData<Boolean>()
    val errorData = MutableLiveData<String>()
    var detailModel: DetailResponse ? = null

    private val _reviewData = MutableLiveData<List<ReviewModel>>()
    val reviewMovie : LiveData<List<ReviewModel>> get() = _reviewData
    private val _trailerData = MutableLiveData<List<TrailerModel>>()
    val trailerMovie : LiveData<List<TrailerModel>> get() = _trailerData


    var reviewPage: Int = 1


    fun onViewLoaded(movieId : Int) {
        getDetailMovie(movieId.toString())
        getReviewMovie(movieId.toString())
        getTrailerMovie(movieId.toString())
    }




    private fun getDetailMovie(movieId: String) {
        loadingData.postValue(true)
        viewModelScope.launch {
            movieRepository.detailMovie(movieId).collect{
                when(it){
                    is ErrorResult -> {
                        loadingData.postValue(false)
                        errorData.postValue(it.message.orEmpty())
                    }
                    is SuccessResult -> {
                        loadingData.postValue(false)
                        detailModel = it.data
                        _detailMovieData.value = it.data!!
                    }
                }
            }
        }

    }



    fun getReviewMovie(movieId: String){
        loadingReviewData.postValue(true)
        viewModelScope.launch {
            movieRepository.reviewMovie(movieId, reviewPage).collect{
                when(it){
                    is ErrorResult -> {
                        loadingReviewData.postValue(false)
                        errorData.postValue(it.message.orEmpty())
                    }
                    is SuccessResult -> {
                        loadingReviewData.postValue(false)
                        _reviewData.value = it.data!!
                    }
                }
            }
        }

    }


    private fun getTrailerMovie(movieId: String) {
        loadingTrailer.postValue(true)
        viewModelScope.launch {
            movieRepository.trailerMovie(movieId).collect{
                when(it){
                    is ErrorResult -> {
                        loadingReviewData.postValue(false)
                        errorData.postValue(it.message.orEmpty())
                    }
                    is SuccessResult -> {
                        loadingReviewData.postValue(false)
                        _trailerData.value = it.data!!
                    }
                }
            }
        }

    }


}