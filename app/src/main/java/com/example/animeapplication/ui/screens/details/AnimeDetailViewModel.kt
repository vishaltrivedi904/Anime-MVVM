package com.example.animeapplication.ui.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapplication.data.api.NetworkResponse
import com.example.animeapplication.data.model.details.AnimeDetails
import com.example.animeapplication.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(private val repository: AnimeRepository) :
    ViewModel() {

    private val _animeDetail = MutableLiveData<NetworkResponse<AnimeDetails>>()
    val animeDetail: LiveData<NetworkResponse<AnimeDetails>> = _animeDetail

    fun fetchAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            try {
                val result = repository.fetchAnimeDetails(animeId)
                if (result.isSuccessful) {
                    _animeDetail.value = NetworkResponse.Success(result.body()!!)
                } else {
                    _animeDetail.value = NetworkResponse.Error(
                        "Error: " + result.code(),
                        null,
                        result.code()
                    )
                }
            } catch (throwable: Throwable) {
                _animeDetail.value = NetworkResponse.Error("Error", null, 500)
            }
        }
    }
}