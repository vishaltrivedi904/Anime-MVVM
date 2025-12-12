package com.example.animeapplication.core.network

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

sealed class NetworkError {
    data object NoInternet : NetworkError()
    data object Timeout : NetworkError()
    data object SslError : NetworkError()
    data object Unauthorized : NetworkError()
    data object Forbidden : NetworkError()
    data object NotFound : NetworkError()
    data object ServerError : NetworkError()
    data object ParseError : NetworkError()
    data object Unknown : NetworkError()
}

object NetworkErrorHandler {

    fun parse(throwable: Throwable): NetworkError {
        return when (throwable) {
            is UnknownHostException -> NetworkError.NoInternet
            is SocketTimeoutException -> NetworkError.Timeout
            is SSLHandshakeException,
            is SSLPeerUnverifiedException,
            is SSLException -> NetworkError.SslError
            is HttpException -> parseHttpError(throwable.code())
            is IOException -> NetworkError.NoInternet
            else -> NetworkError.Unknown
        }
    }

    private fun parseHttpError(code: Int): NetworkError {
        return when (code) {
            401 -> NetworkError.Unauthorized
            403 -> NetworkError.Forbidden
            404 -> NetworkError.NotFound
            in 500..599 -> NetworkError.ServerError
            else -> NetworkError.Unknown
        }
    }
}
