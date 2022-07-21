package com.example.challengechapter7.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.challengechapter7.data.api.PopularResponse.Popular

class DiffUtilsSecond(
    private val oldlistMoviePopular: List<Popular>,
    private val newlistMoviePopular: List<Popular>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlistMoviePopular.size
    }

    override fun getNewListSize(): Int {
        return newlistMoviePopular.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlistMoviePopular[oldItemPosition].id == newlistMoviePopular[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldlistMoviePopular[oldItemPosition].id != newlistMoviePopular[newItemPosition].id -> {
                false
            }
            oldlistMoviePopular[oldItemPosition].poster_path != newlistMoviePopular[newItemPosition].poster_path -> {
                false
            }
            else -> true
        }
    }
}