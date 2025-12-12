package com.example.animeapplication.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapplication.domain.model.AnimeDetail
import com.example.animeapplication.domain.model.Result
import com.example.animeapplication.domain.usecase.GetAnimeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getAnimeDetail: GetAnimeDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val animeId: Int = checkNotNull(savedStateHandle["animeId"])

    private val _state = MutableStateFlow<Result<AnimeDetail>>(Result.Loading)
    val state: StateFlow<Result<AnimeDetail>> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            getAnimeDetail(animeId).collect { _state.value = it }
        }
    }
}
