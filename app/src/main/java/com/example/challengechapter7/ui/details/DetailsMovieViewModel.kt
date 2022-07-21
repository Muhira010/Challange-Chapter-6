package com.example.challengechapter7.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapter7.data.api.DetailsResponse.DetailsMovieResponse
import com.example.challengechapter7.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(private val repo: MovieRepository): ViewModel(){
    val showDetailsMovie: MutableLiveData<DetailsMovieResponse> = MutableLiveData()
    val showGenresMovie: MutableLiveData<DetailsMovieResponse> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()

    fun getMovieDetails(tmdbID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            showLoading.postValue(true)
            val result = repo.getMovieDetails(tmdbID)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showDetailsMovie.postValue(data)
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

    fun getMovieGenres(tmdbID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getMovieDetails(tmdbID)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showGenresMovie.postValue(data)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }
}