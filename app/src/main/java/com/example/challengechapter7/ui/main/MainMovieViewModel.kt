package com.example.challengechapter7.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapter7.data.api.PlayingResponse.PlayingMovieResponse
import com.example.challengechapter7.data.api.PopularResponse.PopularMovieResponse
import com.example.challengechapter7.data.api.TopRatedResponse.TopRatedMovieResponse
import com.example.challengechapter7.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainMovieViewModel @Inject constructor(private val repo: MovieRepository): ViewModel() {
    val showMoviePlaying: MutableLiveData<PlayingMovieResponse> = MutableLiveData()
    val showMoviePopular: MutableLiveData<PopularMovieResponse> = MutableLiveData()
    val showMovieTopRated: MutableLiveData<TopRatedMovieResponse> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()

    fun getMoviePlaying(){
        CoroutineScope(Dispatchers.IO).launch {
            showLoading.postValue(true)
            val result = repo.getMoviePlaying()
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showMoviePlaying.postValue(data)
                    showLoading.postValue(false)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                    showLoading.postValue(false)
                }
            }
        }
    }

    fun getMoviePopular(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getMoviePopular()
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showMoviePopular.postValue(data)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }

    fun getMovieTopRated(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getMovieTopRated()
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showMovieTopRated.postValue(data)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }
}