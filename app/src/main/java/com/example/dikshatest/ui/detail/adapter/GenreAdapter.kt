package com.example.dikshatest.ui.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dikshatest.databinding.ItemGenreBinding

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {


    var listGenre = listOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateListGenre(items: List<String>){
        listGenre = items
        notifyDataSetChanged()

    }

    class ViewHolder (val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: String){
            binding.tvgenre.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if(listGenre.size > 3){
            3
        } else {
            listGenre.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listGenre[position])
    }
}