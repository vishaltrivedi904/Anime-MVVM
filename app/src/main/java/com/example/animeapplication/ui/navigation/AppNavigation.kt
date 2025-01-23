package com.example.animeapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animeapplication.ui.screens.details.AnimeDetailScreen
import com.example.animeapplication.ui.screens.home.AnimeListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "animeList") {
        composable("animeList") {
            AnimeListScreen { animeId ->
                navController.navigate("animeDetail/$animeId")
            }
        }
        composable("animeDetail/{animeId}") { backStackEntry ->
            val animeId = backStackEntry.arguments?.getString("animeId")?.toIntOrNull()
            animeId?.let {
                AnimeDetailScreen(navController, it)
            }
        }
    }
}