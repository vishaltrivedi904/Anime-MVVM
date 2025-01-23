package com.example.animeapplication.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animeapplication.data.datasource.HomePagingSource
import com.example.animeapplication.data.model.home.Data

import com.example.animeapplication.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(private val repository: AnimeRepository) : ViewModel() {

    private var _animeList: MutableStateFlow<PagingData<Data>> =
        MutableStateFlow(PagingData.empty())
    val animeList: StateFlow<PagingData<Data>> = _animeList.asStateFlow()


    fun fetchTopAnime() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { HomePagingSource(repository) } // Create a new instance here
            ).flow.cachedIn(viewModelScope).collect {
                _animeList.value = it
            }

        }
    }

}