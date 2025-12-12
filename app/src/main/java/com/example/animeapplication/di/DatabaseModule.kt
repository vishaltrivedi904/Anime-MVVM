package com.example.animeapplication.di

import android.content.Context
import androidx.room.Room
import com.example.animeapplication.data.local.AnimeDatabase
import com.example.animeapplication.data.local.dao.AnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AnimeDatabase {
        return Room.databaseBuilder(
            context,
            AnimeDatabase::class.java,
            "anime_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAnimeDao(db: AnimeDatabase): AnimeDao = db.animeDao()
}
