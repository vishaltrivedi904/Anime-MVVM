package com.example.animeapplication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.animeapplication.R
import com.example.animeapplication.core.network.NetworkError
import com.example.animeapplication.core.network.NetworkErrorHandler
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

@Composable
fun ErrorView(
    error: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val networkError = NetworkErrorHandler.parse(error)
    ErrorView(networkError = networkError, onRetry = onRetry, modifier = modifier)
}

@Composable
fun ErrorView(
    networkError: NetworkError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val message = when (networkError) {
        is NetworkError.NoInternet -> stringResource(R.string.error_no_internet)
        is NetworkError.Timeout -> stringResource(R.string.error_timeout)
        is NetworkError.SslError -> stringResource(R.string.error_ssl)
        is NetworkError.Unauthorized -> stringResource(R.string.error_unauthorized)
        is NetworkError.Forbidden -> stringResource(R.string.error_forbidden)
        is NetworkError.NotFound -> stringResource(R.string.error_not_found)
        is NetworkError.ServerError -> stringResource(R.string.error_server)
        is NetworkError.ParseError -> stringResource(R.string.error_parse)
        is NetworkError.Unknown -> stringResource(R.string.error_unknown)
    }

    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}
