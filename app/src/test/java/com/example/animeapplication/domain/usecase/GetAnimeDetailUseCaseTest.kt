package com.example.animeapplication.domain.usecase

import app.cash.turbine.test
import com.example.animeapplication.core.network.NetworkError
import com.example.animeapplication.domain.model.AnimeDetail
import com.example.animeapplication.domain.model.Result
import com.example.animeapplication.domain.repository.AnimeRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAnimeDetailUseCaseTest {

    private lateinit var repository: AnimeRepository
    private lateinit var useCase: GetAnimeDetailUseCase

    private val testAnime = AnimeDetail(
        id = 1,
        title = "Test Anime",
        titleEnglish = "Test Anime English",
        titleJapanese = "テストアニメ",
        imageUrl = "https://example.com/image.jpg",
        episodes = 24,
        score = 8.5,
        status = "Finished Airing",
        synopsis = "Test synopsis",
        year = 2023,
        rating = "PG-13",
        genres = listOf("Action", "Adventure"),
        studios = listOf("Studio A"),
        trailerUrl = "https://youtube.com/embed/test",
        trailerThumbnail = null
    )

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetAnimeDetailUseCase(repository)
    }

    @Test
    fun `invoke returns loading then success`() = runTest {
        every { repository.getAnimeDetail(1) } returns flowOf(
            Result.Loading,
            Result.Success(testAnime)
        )

        useCase(1).test {
            assertTrue(awaitItem() is Result.Loading)
            val success = awaitItem()
            assertTrue(success is Result.Success)
            assertEquals(testAnime, (success as Result.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns error on failure`() = runTest {
        every { repository.getAnimeDetail(1) } returns flowOf(
            Result.Loading,
            Result.Error(NetworkError.NotFound)
        )

        useCase(1).test {
            assertTrue(awaitItem() is Result.Loading)
            val error = awaitItem()
            assertTrue(error is Result.Error)
            assertEquals(NetworkError.NotFound, (error as Result.Error).error)
            awaitComplete()
        }
    }
}
