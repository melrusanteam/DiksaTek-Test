package com.example.dikshatest.ui.all_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dikshatest.R
import com.example.dikshatest.databinding.ActivityMovieBinding
import com.example.dikshatest.ui.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {


    lateinit var binding : ActivityMovieBinding

    private val movieViewModels by viewModels<MovieViewModel>()

    lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindViewModel()
        bindViewEvents()

    }


    fun initView() {
        adapter = MovieAdapter()
        movieViewModels.getAllMovies()
    }

    fun bindViewModel() {
        movieViewModels.popularMovies.observe(this){
            adapter.updateMovies(it)
            binding.rvMovie.apply {
                adapter = this@MovieActivity.adapter
                layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayoutManager.VERTICAL, false)
            }
        }

        movieViewModels.loadingMovies.observe(this){
            if(it)
                binding.loadingMovies.visibility = View.VISIBLE
            else
                binding.loadingMovies.visibility = View.GONE
        }


        movieViewModels.errorLoadMovies.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }


    }

    fun bindViewEvents() {


    }
}