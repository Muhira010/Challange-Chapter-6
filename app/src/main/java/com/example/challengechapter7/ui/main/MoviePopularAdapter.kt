package com.example.challengechapter7.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter7.data.api.PopularResponse.Popular
import com.example.challengechapter7.databinding.ListMoviePlayingBinding
import com.example.challengechapter7.utils.DiffUtilsSecond

class MoviePopularAdapter(
    private val listener : EventListener
): RecyclerView.Adapter<MoviePopularAdapter.ViewHolder>() {
    private var listMoviePopular = emptyList<Popular>()
    inner class ViewHolder(val binding: ListMoviePlayingBinding): RecyclerView.ViewHolder(binding.root)
    companion object{
        const val imageTMDB = "https://image.tmdb.org/t/p/w500/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMoviePlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = listMoviePopular[position]
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
        return listMoviePopular.size
    }

    fun setData(newListMoviePopular: List<Popular>){
        val diffUtil = DiffUtilsSecond(listMoviePopular, newListMoviePopular)
        val diffResult =  DiffUtil.calculateDiff(diffUtil)
        listMoviePopular = newListMoviePopular
        diffResult.dispatchUpdatesTo(this)
    }

    interface EventListener {
        fun onClick(itemList: Popular)
    }

//    private var onItemClickListener: ((Playing) -> Unit)? = null
//
//    fun setOnItemClickListener(listener: (Playing) -> Unit) {
//        onItemClickListener = listener
//    }
}