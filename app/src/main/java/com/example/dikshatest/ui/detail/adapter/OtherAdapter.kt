package com.example.dikshatest.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.recyclerview.widget.RecyclerView
import com.example.dikshatest.data.remote.model.TrailerModel
import com.example.dikshatest.databinding.ItemTrailerBinding

class OtherAdapter : RecyclerView.Adapter<OtherAdapter.ViewHolder>() {

    var listTrailer = listOf<TrailerModel>()

    lateinit var binding: ItemTrailerBinding


    class ViewHolder(val binding: ItemTrailerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrailerModel) {
            val video =
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${item.key}\" title=\"${item.name}\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; \"></iframe>"

            binding.wvTrailer.apply {
                if(itemView.context != null) {
                    loadData(video, "text/html", "utf-8")
                    settings.javaScriptEnabled = true
                    webChromeClient = WebChromeClient()
                }
            }
            binding.tvTitleTrailer.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemTrailerBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTrailer.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTrailer[position])
    }

    fun updateItem(it: List<TrailerModel>) {
        listTrailer = it
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        binding.wvTrailer.apply {
            webChromeClient = null
            stopLoading()
            clearHistory()
            removeAllViews()
            destroy()
        }
        super.onViewRecycled(holder)

    }
}