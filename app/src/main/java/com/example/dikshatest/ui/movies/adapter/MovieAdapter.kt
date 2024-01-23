package com.example.dikshatest.ui.movies.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.databinding.ItemInfiniteLoadingBinding
import com.example.dikshatest.databinding.ItemMovieBinding
import com.example.dikshatest.ui.utils.MovieConstant

class MovieAdapter(
    val listener: EventListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var listMovie = mutableListOf<MovieModel>()
    var loadingMore = false

    private val MOVIE_ITEM = 1
    private val LOADING_ITEM = 0


    inner class MovieHolder(val context: Context, val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : MovieModel) {
            binding.tvTitle.text = item.title
            binding.tvVoteAverage.text = item.voteAverage.toString()
            binding.tvReleaseDate.text = item.releaseDate

            val imageUrl = "${MovieConstant.imageBaseURL}${item.posterPath}"
            Glide.with(context)
                .load(imageUrl)
                .into(binding.imgMovie)

        }
    }

    inner class LoadingViewHolder(val context: Context, val binding: ItemInfiniteLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            MOVIE_ITEM -> {
                val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieHolder(parent.context, binding)
            }
            LOADING_ITEM -> {
                val binding = ItemInfiniteLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(parent.context, binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MovieHolder -> {
                holder.bind(listMovie[position])
                holder.itemView.setOnClickListener {
                    listener.onClickItem(listMovie[position])
                }
            }
            is LoadingViewHolder -> {
                holder.binding.progressBar.visibility = View.VISIBLE
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && loadingMore) {
            LOADING_ITEM
        } else {
            MOVIE_ITEM
        }
    }
    override fun getItemCount(): Int {
        return listMovie.size + if (loadingMore) 1 else 0
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(it: List<MovieModel>) {
        this.listMovie.addAll(it)
        notifyDataSetChanged()
    }

    interface EventListener {
        fun onClickItem(item: MovieModel)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setLoading(loading: Boolean){
        this.loadingMore = loading

        notifyDataSetChanged()
    }

}