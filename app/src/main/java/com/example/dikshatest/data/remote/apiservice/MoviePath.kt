package com.example.dikshatest.data.remote.apiservice

class MoviePath {

    companion object {
        const val popularMovie = "3/movie/popular"
        const val detailMovie = "3/movie/{movieId}"
        const val reviewMovie = "3/movie/{movieId}/reviews"
        const val trailerMovie = "3/movie/{movieId}/videos"
        const val listGenre = "3/genre/movie/list"
        const val discoverMovie = "3/discover/movie"
    }
}