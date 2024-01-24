package com.example.dikshatest.ui.discover

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.compose.ui.text.capitalize
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dikshatest.R
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.databinding.ActivityDiscoverMovieBinding
import com.example.dikshatest.ui.detail.DetailMovieActivity
import com.example.dikshatest.ui.movies.MovieActivity
import com.example.dikshatest.ui.movies.adapter.MovieAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DiscoverMovieActivity : AppCompatActivity() {

    lateinit var binding: ActivityDiscoverMovieBinding

    private val viewModel : DiscoverMovieViewModel by viewModels()

    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindViewModel()
        bindViewEvents()

    }


    fun initView() {
        val genreModel = Gson().fromJson(intent.getStringExtra("genre"), GenreModel::class.java)
        initActionBar(genreModel)
        movieAdapter = MovieAdapter(object: MovieAdapter.EventListener {
            override fun onClickItem(item: MovieModel) {
                startActivity(
                    Intent(this@DiscoverMovieActivity, DetailMovieActivity::class.java).putExtra(
                        "movieId", item.id
                    )
                )
            }
        })

        viewModel.onViewLoaded(genreModel)
    }

    private fun initActionBar(genreModel: GenreModel){
        supportActionBar?.title = genreModel.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun bindViewModel() {

        viewModel.movieData.observe(this) {
            if (it.isNotEmpty()) {
                movieAdapter.updateMovies(it)
                binding.rvMovie.apply {
                    adapter = this@DiscoverMovieActivity.movieAdapter
                    layoutManager =
                        LinearLayoutManager(this@DiscoverMovieActivity, LinearLayoutManager.VERTICAL, false)
                }
            }

        }

        viewModel.loadingMovies.observe(this) {

            if (viewModel.currentPage == 1) {
                if (it) {
                    binding.loadingMovies.visibility = View.VISIBLE
                } else {
                    binding.loadingMovies.visibility = View.GONE
                }
            } else if (viewModel.currentPage > 1) {
                movieAdapter.setLoading(it)
            }

        }


        viewModel.errorMovies.observe(this) {
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

                if (!viewModel.loadingMovies.value!! && lastVisibleItemPosition == totalItemCount - 1) {
                    viewModel.loadMovies()
                }
            }

        })
    }


}