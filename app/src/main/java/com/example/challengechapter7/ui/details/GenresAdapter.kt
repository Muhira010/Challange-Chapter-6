package com.example.challengechapter7.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.challengechapter7.data.api.DetailsResponse.Genres
import com.example.challengechapter7.data.api.PopularResponse.Popular
import com.example.challengechapter7.databinding.ListMovieGenreBinding
import com.example.challengechapter7.utils.DiffUtilsThird

class GenresAdapter(): RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    private var listGenres = emptyList<Genres>()
    inner class ViewHolder(val binding: ListMovieGenreBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMovieGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemList = listGenres[position]
        holder.binding.tvGenres.text = itemList.name
    }

    override fun getItemCount(): Int {
        return listGenres.size
    }

    fun setData(newListMovieGenres: List<Genres>){
        val diffUtil = DiffUtilsThird(listGenres, newListMovieGenres)
        val diffResult =  DiffUtil.calculateDiff(diffUtil)
        listGenres = newListMovieGenres
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