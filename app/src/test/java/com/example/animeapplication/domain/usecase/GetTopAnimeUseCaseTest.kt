package com.example.animeapplication.domain.usecase

import androidx.paging.PagingData
import com.example.animeapplication.domain.model.Anime
import com.example.animeapplication.domain.repository.AnimeRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class GetTopAnimeUseCaseTest {

    private lateinit var repository: AnimeRepository
    private lateinit var useCase: GetTopAnimeUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTopAnimeUseCase(repository)
    }

    @Test
    fun `invoke returns paging data from repository`() = runTest {
        every { repository.getTopAnime() } returns flowOf(PagingData.empty())

        val result = useCase()

        assertNotNull(result)
    }
}
