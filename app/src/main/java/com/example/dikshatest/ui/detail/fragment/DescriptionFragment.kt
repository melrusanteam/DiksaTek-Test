package com.example.dikshatest.ui.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dikshatest.R
import com.example.dikshatest.databinding.FragmentDescriptionBinding
import com.example.dikshatest.ui.detail.DetailViewModel
import com.example.dikshatest.ui.detail.adapter.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionFragment : Fragment() {

    lateinit var binding: FragmentDescriptionBinding
    private val detailViewModel: DetailViewModel by activityViewModels()
    lateinit var genreAdapter: GenreAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        bindViewEvents()
        bindViewModel()
    }


    fun initView() {
        genreAdapter = GenreAdapter()

    }

    fun bindViewModel() {

        detailViewModel.detailMovie.observe(viewLifecycleOwner) {
            binding.tvDescription.text = it.overview
            val dataGenre = mutableListOf<String>()
            it.genres?.map {
                dataGenre.add(it.name)
            }
            genreAdapter.updateListGenre(dataGenre)
            binding.rvGenre.apply {
                adapter = genreAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            binding.tvVote.text = it.voteAverage.toString()
            binding.tvCount.text = it.voteCount.toString()
            binding.tvPopularity.text = it.popularity.toString()
            binding.tvBudget.text = it.budget.toString()
            binding.tvRevenue.text = it.revenue.toString()

        }
    }

    fun bindViewEvents() {

    }
}