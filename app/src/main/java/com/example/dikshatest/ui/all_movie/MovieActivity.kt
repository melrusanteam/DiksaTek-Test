package com.example.dikshatest.ui.all_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dikshatest.R
import com.example.dikshatest.databinding.ActivityMovieBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {


    lateinit var binding : ActivityMovieBinding

    private val movieViewModels by viewModels<MovieViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindViewModel()
        bindViewEvents()

    }


    fun initView() {

        movieViewModels.getAllMovies()
    }

    fun bindViewModel() {
        movieViewModels.popularMovies.observe(this){
            println("Success load movies ${it.size}")
        }

        movieViewModels.loadingMovies.observe(this){
            if(it)
                println("loading")
            else
                println("close loading")
        }


        movieViewModels.errorLoadMovies.observe(this){
            println("failed load movie")
            println(it)
        }


    }

    fun bindViewEvents() {


    }
}