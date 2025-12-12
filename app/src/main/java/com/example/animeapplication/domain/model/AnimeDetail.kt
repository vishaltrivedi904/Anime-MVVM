package com.example.animeapplication.domain.model

data class AnimeDetail(
    val id: Int,
    val title: String,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val imageUrl: String,
    val episodes: Int?,
    val score: Double?,
    val status: String,
    val synopsis: String?,
    val year: Int?,
    val rating: String?,
    val genres: List<String>,
    val studios: List<String>,
    val trailerUrl: String?,
    val trailerThumbnail: String?
) {
    val displayTitle: String get() = titleEnglish ?: title
    val scoreText: String get() = score?.let { String.format("%.1f", it) } ?: "N/A"
    val episodesText: String get() = episodes?.toString() ?: "?"
    val hasTrailer: Boolean get() = !trailerUrl.isNullOrBlank()
}
