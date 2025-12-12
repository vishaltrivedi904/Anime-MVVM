package com.example.animeapplication.core.security

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "app_prefs")

@Singleton
class SecureStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val encryptedPrefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // Regular preferences
    suspend fun saveString(key: String, value: String) {
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = value
        }
    }

    suspend fun getString(key: String): String? {
        return context.dataStore.data.map { prefs ->
            prefs[stringPreferencesKey(key)]
        }.first()
    }

    // Encrypted preferences for sensitive data
    fun saveSecure(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }

    fun getSecure(key: String): String? {
        return encryptedPrefs.getString(key, null)
    }

    fun removeSecure(key: String) {
        encryptedPrefs.edit().remove(key).apply()
    }
}
