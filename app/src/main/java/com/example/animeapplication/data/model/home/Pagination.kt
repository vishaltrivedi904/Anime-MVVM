package com.example.animeapplication.data.model.home

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: com.example.animeapplication.data.model.home.Items,
    val last_visible_page: Int
)