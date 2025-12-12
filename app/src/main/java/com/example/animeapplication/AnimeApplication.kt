package com.example.animeapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class with Hilt dependency injection.
 */
@HiltAndroidApp
class AnimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Security and initialization logic handled by SecurityManager injection
    }
}
