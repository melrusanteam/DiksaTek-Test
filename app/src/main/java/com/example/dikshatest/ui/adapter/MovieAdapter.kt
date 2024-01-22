package com.example.dikshatest.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.databinding.ItemMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    var listMovie = listOf<MovieModel>()


    class ViewHolder(val context: Context, val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bind(item : MovieModel) {
            binding.tvTitle.text = item.title
            binding.tvVoteAverage.text = item.voteAverage.toString()
            val imageUrl = "${"https://image.tmdb.org/t/p/w500/"}${item.posterPath}"
            Glide.with(context)
                .load(imageUrl)
                .into(binding.imgMovie)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(parent.context, binding)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listMovie[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            println(item.title)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(it: List<MovieModel>) {
        this.listMovie = it
        notifyDataSetChanged()
    }
}