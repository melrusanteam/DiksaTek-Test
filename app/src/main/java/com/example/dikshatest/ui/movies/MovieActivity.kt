package com.example.dikshatest.ui.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.databinding.ActivityMovieBinding
import com.example.dikshatest.ui.movies.adapter.MovieAdapter
import com.example.dikshatest.ui.detail.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {


    lateinit var binding : ActivityMovieBinding

    private val movieViewModels by viewModels<MovieViewModel>()

    lateinit var adapter: MovieAdapter
    var loadingMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindViewModel()
        bindViewEvents()

    }


    fun initView() {
        adapter = MovieAdapter(
            object: MovieAdapter.EventListener{
                override fun onClickItem(item: MovieModel) {
                    startActivity(Intent(this@MovieActivity, DetailMovieActivity::class.java).putExtra(
                        "movieId", item.id)
                    )
                }

            }
        )
        movieViewModels.getAllMovies()
    }

    fun bindViewModel() {
        movieViewModels.allMovies.observe(this){
            adapter.updateMovies(it)
            binding.rvMovie.apply {
                adapter = this@MovieActivity.adapter
                layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayoutManager.VERTICAL, false)
            }
        }

        movieViewModels.loadingMovies.observe(this){

            if(movieViewModels.currentPage == 1){
               if(it){
                   binding.loadingMovies.visibility = View.VISIBLE
               } else{
                   binding.loadingMovies.visibility = View.GONE
               }
            } else if(movieViewModels.currentPage > 1){
                adapter.setLoading(it)
            }

        }


        movieViewModels.errorLoadMovies.observe(this){
            binding.tvErrorMessage.apply {
                visibility = View.VISIBLE
                text = it
            }
        }


    }

    fun bindViewEvents() {

        binding.rvMovie.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if(!movieViewModels.loadingMovies.value!! && lastVisibleItemPosition == totalItemCount - 1 ){
                    movieViewModels.getAllMovies()
                }
            }
        })

    }
}