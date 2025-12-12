package com.example.animeapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animeapplication.presentation.screen.DetailScreen
import com.example.animeapplication.presentation.screen.HomeScreen
import com.example.animeapplication.presentation.screen.SecurityScreen
import com.example.animeapplication.presentation.screen.SettingsScreen
import com.example.animeapplication.presentation.viewmodel.SettingsViewModel

sealed class Screen(val route: String) {
    data object Security : Screen("security")
    data object Home : Screen("home")
    data object Settings : Screen("settings")
    data object Detail : Screen("detail/{animeId}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}

@Composable
fun AppNavigation(
    startDestination: String,
    onExitApp: () -> Unit,
    onLanguageChange: (String) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Security.route) {
            SecurityScreen(
                onProceed = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Security.route) { inclusive = true }
                    }
                },
                onExit = onExitApp
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onAnimeClick = { id ->
                    navController.navigate(Screen.Detail.createRoute(id))
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
            val language by viewModel.language.collectAsStateWithLifecycle()

            SettingsScreen(
                isDarkTheme = isDarkTheme,
                currentLanguage = language,
                onThemeChange = { viewModel.setDarkTheme(it) },
                onLanguageChange = { code ->
                    viewModel.setLanguage(code)
                    onLanguageChange(code)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) {
            DetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
