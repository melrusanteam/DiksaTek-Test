package com.example.dikshatest.ui.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.databinding.ActivityMovieBinding
import com.example.dikshatest.ui.choose_genre.ChooseGenreActivity
import com.example.dikshatest.ui.movies.adapter.MovieAdapter
import com.example.dikshatest.ui.detail.DetailMovieActivity
import com.example.dikshatest.ui.detail.adapter.GenreAdapter
import com.example.dikshatest.ui.movies.adapter.GenreMovieAdapter
import com.example.dikshatest.ui.utils.MovieConstant
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {


    lateinit var binding: ActivityMovieBinding

    private val movieViewModels by viewModels<MovieViewModel>()

    lateinit var adapter: MovieAdapter
    private lateinit var listMovieAdapter: GenreMovieAdapter
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
            object : MovieAdapter.EventListener {
                override fun onClickItem(item: MovieModel) {
                    startActivity(
                        Intent(this@MovieActivity, DetailMovieActivity::class.java).putExtra(
                            "movieId", item.id
                        )
                    )
                }

            },
        )

        listMovieAdapter = GenreMovieAdapter(
            object : GenreMovieAdapter.EventListener {
                override fun onClickItem(item: GenreModel) {
                    when (item.name) {
                        "choose genre" -> {
                            startActivity(Intent(this@MovieActivity, ChooseGenreActivity::class.java))
                        }
                        else -> println(item)
                    }
                }
            },
            initialSelectedPosition = 0
        )
        val listMovie = MovieConstant.tempListGenre
        listMovieAdapter.updateGenreList(listMovie)
        binding.rvGenre.apply {
            adapter = listMovieAdapter
            layoutManager =
                LinearLayoutManager(this@MovieActivity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = null
        }
        movieViewModels.getAllMovies()
    }

    fun bindViewModel() {
        movieViewModels.allMovies.observe(this) {
            if (it.isNotEmpty()) {
                adapter.updateMovies(it)
                binding.rvMovie.apply {
                    adapter = this@MovieActivity.adapter
                    layoutManager =
                        LinearLayoutManager(this@MovieActivity, LinearLayoutManager.VERTICAL, false)
                }
            }

        }

        movieViewModels.loadingMovies.observe(this) {

            if (movieViewModels.currentPage == 1) {
                if (it) {
                    binding.loadingMovies.visibility = View.VISIBLE
                } else {
                    binding.loadingMovies.visibility = View.GONE
                }
            } else if (movieViewModels.currentPage > 1) {
                adapter.setLoading(it)
            }

        }


        movieViewModels.errorLoadMovies.observe(this) {
            binding.tvErrorMessage.apply {
                visibility = View.VISIBLE
                text = it
            }
        }


    }

    fun bindViewEvents() {

        binding.rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!movieViewModels.loadingMovies.value!! && lastVisibleItemPosition == totalItemCount - 1) {
                    movieViewModels.getAllMovies()
                }
            }
        })

    }
}