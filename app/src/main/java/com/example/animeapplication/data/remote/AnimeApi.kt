package com.example.animeapplication.data.remote

import com.example.animeapplication.data.model.AnimeDetailResponse
import com.example.animeapplication.data.model.AnimeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15
    ): Response<AnimeListResponse>

    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): Response<AnimeDetailResponse>
}
