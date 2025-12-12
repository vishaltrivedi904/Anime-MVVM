package com.example.animeapplication.domain.model

import com.example.animeapplication.core.network.NetworkError

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: NetworkError) : Result<Nothing>()
}
