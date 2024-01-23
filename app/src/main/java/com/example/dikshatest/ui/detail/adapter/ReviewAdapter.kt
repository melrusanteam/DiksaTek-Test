package com.example.dikshatest.ui.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dikshatest.data.remote.model.ReviewModel
import com.example.dikshatest.databinding.ItemReviewBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    private val movieReviews = mutableListOf<ReviewModel>()


    class ViewHolder (val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ReviewModel) {
            binding.tvAuthor.text = item.author
            binding.tvCreatedData.text = item.createdAt
            binding.tvContent.text = item.content

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieReviews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movieReviews[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateReview(it: List<ReviewModel>) {
        movieReviews.addAll(it)
        notifyDataSetChanged()
    }
}