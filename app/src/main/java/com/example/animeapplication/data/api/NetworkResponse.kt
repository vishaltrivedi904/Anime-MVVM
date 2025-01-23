package com.example.animeapplication.data.api

sealed class NetworkResponse<out T> {
    object Loading : NetworkResponse<Nothing>()
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val message: String, val data: Any?, val code: Int? = null) :
        NetworkResponse<Nothing>()
}