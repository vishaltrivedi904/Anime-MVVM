package com.example.animeapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animeapplication.data.local.entity.AnimeEntity

@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<AnimeEntity>)

    @Query("SELECT * FROM anime_cache ORDER BY score DESC")
    suspend fun getAll(): List<AnimeEntity>

    @Query("DELETE FROM anime_cache")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM anime_cache")
    suspend fun count(): Int
}
