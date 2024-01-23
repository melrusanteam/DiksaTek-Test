package com.example.dikshatest.ui.choose_genre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dikshatest.R
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.databinding.ActivityChooseGenreBinding
import com.example.dikshatest.ui.discover.DiscoverMovieActivity
import com.example.dikshatest.ui.movies.adapter.GenreMovieAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChooseGenreActivity : AppCompatActivity() {

    lateinit var binding: ActivityChooseGenreBinding

    private val chooseGenreViewModel by viewModels<ChooseGenreViewModel>()

    lateinit var genreMovieAdapter: ChooseGenreAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindViewModel()
        bindViewEvents()

    }

    fun initView(){
        supportActionBar?.title = getString(R.string.label_select_genre)
        chooseGenreViewModel.getListGenre()
        genreMovieAdapter = ChooseGenreAdapter(object: ChooseGenreAdapter.EventListener{
            override fun onClickItem(item: GenreModel) {
                startActivity(
                    Intent(this@ChooseGenreActivity, DiscoverMovieActivity::class.java)
                    .putExtra("genre", Gson().toJson(item)))
            }
        },)


    }

    fun bindViewModel() {

        chooseGenreViewModel.genreModel.observe(this){
            genreMovieAdapter.updateGenreList(it)
            binding.rvGenre.apply {
                adapter = this@ChooseGenreActivity.genreMovieAdapter
                layoutManager = GridLayoutManager(this@ChooseGenreActivity, 3)
            }
        }

    }

    fun bindViewEvents() {

    }
    }