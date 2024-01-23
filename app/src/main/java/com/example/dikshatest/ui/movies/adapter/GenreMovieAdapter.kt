package com.example.dikshatest.ui.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dikshatest.R
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.databinding.ItemGenreMovieBinding

class GenreMovieAdapter(
    private val listener: EventListener,
    private val initialSelectedPosition: Int? = RecyclerView.NO_POSITION
) : RecyclerView.Adapter<GenreMovieAdapter.ViewHolder>() {

    var listGenre = listOf<GenreModel>()
    private var selectedPosition = initialSelectedPosition

    class ViewHolder(val binding: ItemGenreMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GenreModel, isSelected: Boolean) {
            binding.tvGenre.text = item.name

            if (isSelected) {
                binding.cardGenre.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.colorPrimary)
                )
            } else {
                binding.cardGenre.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.colorPrimaryGrey)
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreMovieBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listGenre.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val isSelected = selectedPosition == position
        holder.bind(listGenre[position], isSelected)
        holder.itemView.setOnClickListener {
            if (selectedPosition != position) {
                val previousSelected = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousSelected!!)
                notifyItemChanged(selectedPosition!!)
            }
            listener.onClickItem(listGenre[position])
        }
    }

    fun updateGenreList(items: List<GenreModel>) {
        listGenre = items
        notifyDataSetChanged()
    }

    interface EventListener {
        fun onClickItem(item: GenreModel)
    }
}