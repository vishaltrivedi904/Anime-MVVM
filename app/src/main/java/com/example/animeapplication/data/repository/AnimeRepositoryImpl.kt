package com.example.animeapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animeapplication.core.network.NetworkError
import com.example.animeapplication.core.network.NetworkErrorHandler
import com.example.animeapplication.data.local.dao.AnimeDao
import com.example.animeapplication.data.local.entity.AnimeEntity
import com.example.animeapplication.data.model.AnimeDto
import com.example.animeapplication.data.remote.AnimeApi
import com.example.animeapplication.domain.model.Anime
import com.example.animeapplication.domain.model.AnimeDetail
import com.example.animeapplication.domain.model.Result
import com.example.animeapplication.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepositoryImpl @Inject constructor(
    private val api: AnimeApi,
    private val dao: AnimeDao
) : AnimeRepository {

    override fun getTopAnime(): Flow<PagingData<Anime>> = Pager(
        config = PagingConfig(pageSize = 15, enablePlaceholders = false),
        pagingSourceFactory = { AnimePagingSource() }
    ).flow

    override fun getAnimeDetail(id: Int): Flow<Result<AnimeDetail>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getAnimeById(id)
            if (response.isSuccessful && response.body() != null) {
                emit(Result.Success(response.body()!!.data.toDetail()))
            } else {
                val error = NetworkErrorHandler.parse(
                    retrofit2.HttpException(response)
                )
                emit(Result.Error(error))
            }
        } catch (e: Exception) {
            emit(Result.Error(NetworkErrorHandler.parse(e)))
        }
    }

    private inner class AnimePagingSource : PagingSource<Int, Anime>() {

        override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
            return state.anchorPosition?.let { pos ->
                state.closestPageToPosition(pos)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
            val page = params.key ?: 1
            return try {
                val response = api.getTopAnime(page = page)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    val items = data.data.map { it.toDomain() }

                    if (page == 1) {
                        dao.clearAll()
                        dao.insertAll(data.data.map { it.toEntity() })
                    }

                    LoadResult.Page(
                        data = items,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (data.pagination?.hasNextPage == true) page + 1 else null
                    )
                } else {
                    loadFromCache(page)
                }
            } catch (e: Exception) {
                loadFromCache(page)
            }
        }

        private suspend fun loadFromCache(page: Int): LoadResult<Int, Anime> {
            if (page != 1) return LoadResult.Error(Exception("No more data"))
            val cached = dao.getAll()
            return if (cached.isNotEmpty()) {
                LoadResult.Page(cached.map { it.toDomain() }, null, null)
            } else {
                LoadResult.Error(Exception("No data available"))
            }
        }
    }
}

// Mappers
private fun AnimeDto.toDomain() = Anime(
    id = id,
    title = title,
    titleEnglish = titleEnglish,
    imageUrl = images?.jpg?.imageUrl ?: "",
    episodes = episodes,
    score = score,
    status = status ?: "Unknown",
    year = year,
    genres = genres?.map { it.name } ?: emptyList()
)

private fun AnimeDto.toDetail() = AnimeDetail(
    id = id,
    title = title,
    titleEnglish = titleEnglish,
    titleJapanese = titleJapanese,
    imageUrl = images?.jpg?.largeImageUrl ?: images?.jpg?.imageUrl ?: "",
    episodes = episodes,
    score = score,
    status = status ?: "Unknown",
    synopsis = synopsis,
    year = year,
    rating = rating,
    genres = genres?.map { it.name } ?: emptyList(),
    studios = studios?.map { it.name } ?: emptyList(),
    trailerUrl = trailer?.embedUrl,
    trailerThumbnail = trailer?.images?.maxImageUrl
)

private fun AnimeDto.toEntity() = AnimeEntity(
    id = id,
    title = title,
    titleEnglish = titleEnglish,
    imageUrl = images?.jpg?.imageUrl ?: "",
    episodes = episodes,
    score = score,
    status = status ?: "Unknown",
    year = year,
    genres = genres?.joinToString(",") { it.name } ?: ""
)

private fun AnimeEntity.toDomain() = Anime(
    id = id,
    title = title,
    titleEnglish = titleEnglish,
    imageUrl = imageUrl,
    episodes = episodes,
    score = score,
    status = status,
    year = year,
    genres = genres.split(",").filter { it.isNotBlank() }
)
