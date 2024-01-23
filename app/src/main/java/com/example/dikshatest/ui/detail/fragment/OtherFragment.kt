package com.example.dikshatest.ui.detail.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.activityViewModels
import com.example.dikshatest.databinding.FragmentTrailerBinding
import com.example.dikshatest.ui.detail.DetailViewModel
import com.example.dikshatest.ui.detail.adapter.OtherAdapter

class OtherFragment : Fragment() {

    lateinit var binding: FragmentTrailerBinding
    lateinit var otherAdapter: OtherAdapter

    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrailerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        bindViewModel()
        bindViewEvents()
    }


    @SuppressLint("SetJavaScriptEnabled")
    fun initView() {

        otherAdapter = OtherAdapter()


    }

    fun bindViewModel() {

        detailViewModel.trailerMovie.observe(viewLifecycleOwner) {
            otherAdapter.updateItem(it)
        }

        val video =
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xD5799ubuNI\" title=\"asdfasdf\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; \"></iframe>"

        binding.wvtest.apply {
            loadData(video, "text/html", "utf-8")
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
        }


    }

    fun bindViewEvents() {


    }
}