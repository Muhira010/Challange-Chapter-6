package com.example.challengechapter7.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.challengechapter7.data.api.DetailsResponse.Genres

class DiffUtilsThird(
    private val oldlistMovieGenres: List<Genres>,
    private val newlistMovieGenres: List<Genres>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlistMovieGenres.size
    }

    override fun getNewListSize(): Int {
        return newlistMovieGenres.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlistMovieGenres[oldItemPosition].id == newlistMovieGenres[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldlistMovieGenres[oldItemPosition].id != newlistMovieGenres[newItemPosition].id -> {
                false
            }
            oldlistMovieGenres[oldItemPosition].name != newlistMovieGenres[newItemPosition].name -> {
                false
            }
            else -> true
        }
    }
}