package com.example.animeapplication.domain.repository

import androidx.paging.PagingData
import com.example.animeapplication.domain.model.Anime
import com.example.animeapplication.domain.model.AnimeDetail
import com.example.animeapplication.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getTopAnime(): Flow<PagingData<Anime>>
    fun getAnimeDetail(id: Int): Flow<Result<AnimeDetail>>
}
