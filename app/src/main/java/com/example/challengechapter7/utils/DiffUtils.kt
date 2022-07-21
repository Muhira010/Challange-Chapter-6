package com.example.challengechapter7.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.challengechapter7.data.api.PlayingResponse.Playing

class DiffUtils(
    private val oldlistMoviePlaying: List<Playing>,
    private val newlistMoviePlaying: List<Playing>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlistMoviePlaying.size
    }

    override fun getNewListSize(): Int {
        return newlistMoviePlaying.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlistMoviePlaying[oldItemPosition].id == newlistMoviePlaying[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldlistMoviePlaying[oldItemPosition].id != newlistMoviePlaying[newItemPosition].id -> {
                false
            }
            oldlistMoviePlaying[oldItemPosition].poster_path != newlistMoviePlaying[newItemPosition].poster_path -> {
                false
            }
            else -> true
        }
    }
}