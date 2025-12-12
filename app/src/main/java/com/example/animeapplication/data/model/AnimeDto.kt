package com.example.animeapplication.data.model

import com.google.gson.annotations.SerializedName

data class AnimeListResponse(
    val data: List<AnimeDto>,
    val pagination: PaginationDto?
)

data class AnimeDetailResponse(
    val data: AnimeDto
)

data class PaginationDto(
    @SerializedName("has_next_page") val hasNextPage: Boolean,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("last_visible_page") val lastPage: Int
)

data class AnimeDto(
    @SerializedName("mal_id") val id: Int,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String?,
    @SerializedName("title_japanese") val titleJapanese: String?,
    val images: ImagesDto?,
    val trailer: TrailerDto?,
    val episodes: Int?,
    val status: String?,
    val score: Double?,
    val synopsis: String?,
    val year: Int?,
    val season: String?,
    val rating: String?,
    val genres: List<GenreDto>?,
    val studios: List<StudioDto>?
)

data class ImagesDto(
    val jpg: ImageUrlDto?
)

data class ImageUrlDto(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("large_image_url") val largeImageUrl: String?
)

data class TrailerDto(
    val url: String?,
    @SerializedName("embed_url") val embedUrl: String?,
    val images: TrailerImagesDto?
)

data class TrailerImagesDto(
    @SerializedName("maximum_image_url") val maxImageUrl: String?
)

data class GenreDto(
    @SerializedName("mal_id") val id: Int,
    val name: String
)

data class StudioDto(
    @SerializedName("mal_id") val id: Int,
    val name: String
)
