package com.example.animeapplication.domain.usecase

import com.example.animeapplication.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(id: Int) = repository.getAnimeDetail(id)
}
