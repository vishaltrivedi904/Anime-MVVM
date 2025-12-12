package com.example.animeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.animeapplication.core.security.DeviceSecurityStatus
import com.example.animeapplication.core.security.SecurityManager
import com.example.animeapplication.presentation.navigation.AppNavigation
import com.example.animeapplication.presentation.navigation.Screen
import com.example.animeapplication.presentation.theme.AnimeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var securityManager: SecurityManager

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
            AnimeTheme {
                AppNavigation(
                    startDestination = startDestination,
                    onExitApp = { finish() }
                )
            }
        }
    }
}
