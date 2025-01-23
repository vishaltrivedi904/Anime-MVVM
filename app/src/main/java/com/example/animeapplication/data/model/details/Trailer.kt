package com.example.animeapplication.data.model.details

data class Trailer(
    val embed_url: String,
    val images: com.example.animeapplication.data.model.details.ImagesX,
    val url: String?,
    val youtube_id: String
)