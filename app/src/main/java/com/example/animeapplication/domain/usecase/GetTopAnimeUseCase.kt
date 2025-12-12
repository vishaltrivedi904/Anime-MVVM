package com.example.animeapplication.domain.usecase

import com.example.animeapplication.domain.repository.AnimeRepository
import javax.inject.Inject

class GetTopAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke() = repository.getTopAnime()
}
