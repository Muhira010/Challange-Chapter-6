package com.example.challengechapter7.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.challengechapter7.data.api.DetailsResponse.Genres
import com.example.challengechapter7.data.api.PopularResponse.Popular
import com.example.challengechapter7.data.api.TopRatedResponse.TopRated

class DiffUtilsFourth(
    private val oldlistMovieTopRated: List<TopRated>,
    private val newlistMovieTopRated: List<TopRated>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlistMovieTopRated.size
    }

    override fun getNewListSize(): Int {
        return newlistMovieTopRated.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlistMovieTopRated[oldItemPosition].id == newlistMovieTopRated[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldlistMovieTopRated[oldItemPosition].id != newlistMovieTopRated[newItemPosition].id -> {
                false
            }
            oldlistMovieTopRated[oldItemPosition].poster_path != newlistMovieTopRated[newItemPosition].poster_path -> {
                false
            }
            else -> true
        }
    }
}
