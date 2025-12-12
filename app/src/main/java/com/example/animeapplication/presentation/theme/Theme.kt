package com.example.animeapplication.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColors = darkColorScheme(
    primary = Color(0xFFE94560),
    secondary = Color(0xFFF472B6),
    tertiary = Color(0xFF7C3AED),
    background = Color(0xFF0F0F23),
    surface = Color(0xFF1A1A2E),
    surfaceVariant = Color(0xFF1E1E32),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFFA1A1AA),
    error = Color(0xFFEF4444),
    errorContainer = Color(0xFF7F1D1D)
)

private val LightColors = lightColorScheme(
    primary = Color(0xFFE94560),
    secondary = Color(0xFFEC4899),
    tertiary = Color(0xFF8B5CF6),
    background = Color(0xFFFAFAFA),
    surface = Color.White,
    surfaceVariant = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color(0xFF666666),
    error = Color(0xFFDC2626),
    errorContainer = Color(0xFFFEE2E2)
)

@Composable
fun AnimeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
