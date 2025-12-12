package com.example.animeapplication.core.network

import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class NetworkErrorHandlerTest {

    @Test
    fun `parse returns NoInternet for UnknownHostException`() {
        val result = NetworkErrorHandler.parse(UnknownHostException())
        assertEquals(NetworkError.NoInternet, result)
    }

    @Test
    fun `parse returns Timeout for SocketTimeoutException`() {
        val result = NetworkErrorHandler.parse(SocketTimeoutException())
        assertEquals(NetworkError.Timeout, result)
    }

    @Test
    fun `parse returns SslError for SSLHandshakeException`() {
        val result = NetworkErrorHandler.parse(SSLHandshakeException("test"))
        assertEquals(NetworkError.SslError, result)
    }

    @Test
    fun `parse returns NotFound for 404 HttpException`() {
        val response = Response.error<Any>(404, okhttp3.ResponseBody.create(null, ""))
        val result = NetworkErrorHandler.parse(HttpException(response))
        assertEquals(NetworkError.NotFound, result)
    }

    @Test
    fun `parse returns ServerError for 500 HttpException`() {
        val response = Response.error<Any>(500, okhttp3.ResponseBody.create(null, ""))
        val result = NetworkErrorHandler.parse(HttpException(response))
        assertEquals(NetworkError.ServerError, result)
    }

    @Test
    fun `parse returns Unauthorized for 401 HttpException`() {
        val response = Response.error<Any>(401, okhttp3.ResponseBody.create(null, ""))
        val result = NetworkErrorHandler.parse(HttpException(response))
        assertEquals(NetworkError.Unauthorized, result)
    }

    @Test
    fun `parse returns Unknown for generic exception`() {
        val result = NetworkErrorHandler.parse(RuntimeException("unknown"))
        assertEquals(NetworkError.Unknown, result)
    }
}
