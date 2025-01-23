package com.example.animeapplication.data.api

import com.example.animeapplication.data.model.details.AnimeDetails
import com.example.animeapplication.data.model.home.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<AnimeResponse>

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(@Path("anime_id") animeId: Int): Response<AnimeDetails>


}