package com.example.animeapplication.data.model.home

data class AnimeResponse(
    val `data`: List<com.example.animeapplication.data.model.home.Data>,
    val pagination: com.example.animeapplication.data.model.home.Pagination
)