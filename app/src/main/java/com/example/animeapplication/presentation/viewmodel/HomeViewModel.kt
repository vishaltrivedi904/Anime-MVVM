package com.example.animeapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animeapplication.domain.model.Anime
import com.example.animeapplication.domain.usecase.GetTopAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTopAnime: GetTopAnimeUseCase
) : ViewModel() {

    val animeList: Flow<PagingData<Anime>> = getTopAnime().cachedIn(viewModelScope)
}
