package com.example.animeapplication.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.animeapplication.R
import com.example.animeapplication.domain.model.AnimeDetail
import com.example.animeapplication.domain.model.Result
import com.example.animeapplication.presentation.components.ErrorView
import com.example.animeapplication.presentation.components.TrailerPlayer
import com.example.animeapplication.presentation.components.PosterBanner
import com.example.animeapplication.presentation.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.details), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            when (val s = state) {
                is Result.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is Result.Error -> {
                    ErrorView(
                        networkError = s.error,
                        onRetry = { viewModel.load() },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Result.Success -> {
                    DetailContent(s.data)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailContent(anime: AnimeDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (anime.hasTrailer) {
            TrailerPlayer(url = anime.trailerUrl!!)
        } else {
            PosterBanner(imageUrl = anime.imageUrl)
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = anime.displayTitle,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        anime.titleJapanese?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "⭐ ${anime.scoreText} • ${anime.episodesText} eps • ${anime.status}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        anime.rating?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(12.dp))

        if (anime.genres.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                anime.genres.forEach { genre ->
                    AssistChip(onClick = {}, label = { Text(genre) })
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        Text(
            text = stringResource(R.string.synopsis),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = anime.synopsis ?: stringResource(R.string.no_synopsis),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (anime.studios.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "${stringResource(R.string.studios)}: ${anime.studios.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}
