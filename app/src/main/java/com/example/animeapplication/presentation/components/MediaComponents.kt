package com.example.animeapplication.presentation.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter

@Composable
fun TrailerPlayer(url: String) {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
        }
    }

    DisposableEffect(url) {
        val html = """
            <html><body style="margin:0;background:#000">
            <iframe width="100%" height="100%" src="$url" frameborder="0" allowfullscreen></iframe>
            </body></html>
        """.trimIndent()
        webView.loadData(html, "text/html", "UTF-8")
        onDispose { webView.destroy() }
    }

    AndroidView(
        factory = { webView },
        modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun PosterBanner(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}
