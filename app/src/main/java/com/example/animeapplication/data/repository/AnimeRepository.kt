package com.example.animeapplication.data.repository

import com.example.animeapplication.data.api.ApiInterface
import com.example.animeapplication.data.model.details.AnimeDetails
import com.example.animeapplication.data.model.home.AnimeResponse
import retrofit2.Response
import javax.inject.Inject


class AnimeRepository @Inject constructor(private val apiService: ApiInterface) {

    suspend fun fetchTopAnime(limit: Int, page: Int): Response<AnimeResponse> {
        return apiService.getTopAnime(limit, page)
    }

    suspend fun fetchAnimeDetails(animeId: Int): Response<AnimeDetails> {
        return apiService.getAnimeDetails(animeId)
    }
}