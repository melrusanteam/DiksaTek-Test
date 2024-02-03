package com.example.dikshatest.ui.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.dikshatest.R
import com.example.dikshatest.databinding.FragmentReviewBinding
import com.example.dikshatest.ui.detail.DetailViewModel
import com.example.dikshatest.ui.detail.adapter.ReviewAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReviewFragment : Fragment() {


    lateinit var binding: FragmentReviewBinding


    private val detailViewModel : DetailViewModel by activityViewModels()


    lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel()
        bindViewEvents()
    }


    fun initView(){
        reviewAdapter = ReviewAdapter()



    }

    fun bindViewModel(){
        binding.rvReview.adapter = reviewAdapter
        binding.rvReview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        detailViewModel.reviewMovie.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                reviewAdapter.updateReview(it)
                binding.rvReview.apply {

                    addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                    addOnScrollListener(object: RecyclerView.OnScrollListener() {

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)

                            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                            val visibleItemCount = layoutManager.childCount
                            val totalItemCount = layoutManager.itemCount
                            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                            if (visibleItemCount + firstVisibleItem >= totalItemCount - 1) {
                                detailViewModel.reviewPage++
                                detailViewModel.getReviewMovie(activity?.intent?.getIntExtra("movieId", 0).toString())
                            }

                        }
                    })
                }
            }

        }

    }

    fun bindViewEvents() {

    }

}