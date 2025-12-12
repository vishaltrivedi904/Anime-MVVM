package com.example.animeapplication.core.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

enum class AppLanguage(val code: String, val displayName: String) {
    ENGLISH("en", "English"),
    HINDI("hi", "हिंदी"),
    JAPANESE("ja", "日本語")
}

@Singleton
class SettingsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val darkThemeKey = booleanPreferencesKey("dark_theme")
    private val languageKey = stringPreferencesKey("language")

    val isDarkTheme: Flow<Boolean> = context.settingsDataStore.data.map { prefs ->
        prefs[darkThemeKey] ?: true
    }

    val language: Flow<String> = context.settingsDataStore.data.map { prefs ->
        prefs[languageKey] ?: AppLanguage.ENGLISH.code
    }

    suspend fun setDarkTheme(isDark: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[darkThemeKey] = isDark
        }
    }

    suspend fun setLanguage(languageCode: String) {
        context.settingsDataStore.edit { prefs ->
            prefs[languageKey] = languageCode
        }
    }
}
