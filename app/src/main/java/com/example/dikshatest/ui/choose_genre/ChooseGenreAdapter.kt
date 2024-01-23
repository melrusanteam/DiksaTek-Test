package com.example.dikshatest.ui.choose_genre

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dikshatest.R
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.databinding.ItemChooseGenreBinding
import com.example.dikshatest.databinding.ItemGenreMovieBinding

class ChooseGenreAdapter(
    private val listener: EventListener,
) : RecyclerView.Adapter<ChooseGenreAdapter.ViewHolder>() {

    var listGenre = listOf<GenreModel>()

    class ViewHolder(val binding: ItemChooseGenreBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GenreModel) {
            binding.tvGenre.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChooseGenreBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listGenre.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(listGenre[position])
        holder.itemView.setOnClickListener {
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