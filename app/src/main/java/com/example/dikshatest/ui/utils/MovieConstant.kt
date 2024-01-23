package com.example.dikshatest.ui.utils

import com.example.dikshatest.data.remote.model.GenreModel

class MovieConstant {

    companion object {
        const val imageBaseURL = "https://image.tmdb.org/t/p/w500/"
        val tempListGenre = listOf(
            GenreModel(1, "popular"),
            GenreModel(2, "now playing"),
            GenreModel(3, "top rated"),
            GenreModel(4, "choose genre")
        )
    }

}