package com.example.animeapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_cache")
data class AnimeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val titleEnglish: String?,
    val imageUrl: String,
    val episodes: Int?,
    val score: Double?,
    val status: String,
    val year: Int?,
    val genres: String,
    val cachedAt: Long = System.currentTimeMillis()
)
