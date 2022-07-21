package com.example.challengechapter7.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter7.data.api.PlayingResponse.Playing
import com.example.challengechapter7.databinding.ListMoviePlayingBinding
import com.example.challengechapter7.utils.DiffUtils

class MoviePlayingAdapter(
    private val listener : EventListener
): RecyclerView.Adapter<MoviePlayingAdapter.ViewHolder>() {
    private var listMoviePlaying = emptyList<Playing>()
    inner class ViewHolder(val binding: ListMoviePlayingBinding): RecyclerView.ViewHolder(binding.root)
    companion object{
        const val imageTMDB = "https://image.tmdb.org/t/p/w500/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMoviePlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = listMoviePlaying[position]
        Glide.with(holder.binding.root.context)
            .load(imageTMDB + itemList.poster_path)
            .into(holder.binding.ivPosterPlaying)
        holder.binding.tvTitlePlaying.text = itemList.title
        holder.binding.tvRatingPlaying.text = itemList.vote_average.toString()
        holder.itemView.setOnClickListener {
//            onItemClickListener?.let {
//                it(itemList)
//            }
            listener.onClick(itemList)
        }
    }

    override fun getItemCount(): Int {
        return listMoviePlaying.size
    }

    fun setData(newListMoviePlaying: List<Playing>){
        val diffUtil = DiffUtils(listMoviePlaying, newListMoviePlaying)
        val diffResult =  DiffUtil.calculateDiff(diffUtil)
        listMoviePlaying = newListMoviePlaying
        diffResult.dispatchUpdatesTo(this)
    }

    interface EventListener {
        fun onClick(itemList: Playing)
    }

//    private var onItemClickListener: ((Playing) -> Unit)? = null
//
//    fun setOnItemClickListener(listener: (Playing) -> Unit) {
//        onItemClickListener = listener
//    }
}