package com.example.dikshatest.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dikshatest.R
import com.example.dikshatest.databinding.ActivityDetailMovieBinding

class DetailMovieActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailViewModel>()

    lateinit var binding: ActivityDetailMovieBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
    }
}