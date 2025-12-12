package com.example.animeapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapplication.core.settings.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {

    val isDarkTheme = settingsManager.isDarkTheme.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )

    val language = settingsManager.language.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "en"
    )

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            settingsManager.setDarkTheme(isDark)
        }
    }

    fun setLanguage(code: String) {
        viewModelScope.launch {
            settingsManager.setLanguage(code)
        }
    }
}
