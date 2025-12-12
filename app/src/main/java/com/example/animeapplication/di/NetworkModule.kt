package com.example.animeapplication.di

import com.example.animeapplication.BuildConfig
import com.example.animeapplication.data.remote.AnimeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_HOST = "api.jikan.moe"
    private const val SSL_PIN = "sha256/Sb8dCtDxF3fMIdaM+UeXIdTiKHZvztzF2jjszZEcQd4="

    @Provides
    @Singleton
    fun provideCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(API_HOST, SSL_PIN)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(pinner: CertificatePinner): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .certificatePinner(pinner)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimeApi(retrofit: Retrofit): AnimeApi {
        return retrofit.create(AnimeApi::class.java)
    }
}
