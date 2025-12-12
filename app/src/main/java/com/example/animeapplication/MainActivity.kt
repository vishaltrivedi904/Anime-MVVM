package com.example.animeapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.animeapplication.core.security.DeviceSecurityStatus
import com.example.animeapplication.core.security.SecurityManager
import com.example.animeapplication.core.settings.SettingsManager
import com.example.animeapplication.presentation.navigation.AppNavigation
import com.example.animeapplication.presentation.navigation.Screen
import com.example.animeapplication.presentation.theme.AnimeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var securityManager: SecurityManager

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val securityStatus = securityManager.checkDeviceSecurity()
        val startDestination = when (securityStatus) {
            DeviceSecurityStatus.SECURE -> Screen.Home.route
            DeviceSecurityStatus.ROOTED,
            DeviceSecurityStatus.EMULATOR -> Screen.Security.route
        }

        enableEdgeToEdge()

        setContent {
            val isDarkTheme by settingsManager.isDarkTheme.collectAsStateWithLifecycle(initialValue = true)
            val language by settingsManager.language.collectAsStateWithLifecycle(initialValue = "en")

            // Apply locale
            updateLocale(language)

            AnimeTheme(darkTheme = isDarkTheme) {
                AppNavigation(
                    startDestination = startDestination,
                    onExitApp = { finish() },
                    onLanguageChange = { code ->
                        updateLocale(code)
                        recreate()
                    }
                )
            }
        }
    }

    private fun updateLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
