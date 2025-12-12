package com.example.animeapplication.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val titleEnglish: String?,
    val imageUrl: String,
    val episodes: Int?,
    val score: Double?,
    val status: String,
    val year: Int?,
    val genres: List<String>
) {
    val displayTitle: String get() = titleEnglish ?: title
    val scoreText: String get() = score?.let { String.format("%.1f", it) } ?: "N/A"
    val episodesText: String get() = episodes?.toString() ?: "?"
}
