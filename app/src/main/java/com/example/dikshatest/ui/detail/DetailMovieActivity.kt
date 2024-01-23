package com.example.dikshatest.ui.detail

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.dikshatest.R
import com.example.dikshatest.databinding.ActivityDetailMovieBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailViewModel>()

    lateinit var binding: ActivityDetailMovieBinding

    lateinit var pageAdapter: DetailPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        bindViewModel()
        bindViewEvents()
    }



    fun initView() {
        detailViewModel.onViewLoaded(intent.getIntExtra("movieId",0))
        pageAdapter = DetailPageAdapter(this)
        binding.viewPager.adapter = pageAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            when(position){
                0 -> tab.text = "Description"
                1 -> tab.text = "Review"
            }
        }.attach()

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    fun bindViewModel() {
        detailViewModel.detailMovie.observe(this){

        }

        detailViewModel.trailerMovie.observe(this){

            val video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${it.first().key}\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
            binding.trailerMovie.apply {
                loadData(video, "text/html", "utf-8")
                settings.javaScriptEnabled = true
            }
            binding.trailerMovie.webViewClient = object: WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    println(view?.url)
                    return false
                }
            }
        }

    }


    fun bindViewEvents() {

    }


}