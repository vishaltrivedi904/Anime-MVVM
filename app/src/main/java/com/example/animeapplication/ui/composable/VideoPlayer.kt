package com.example.animeapplication.ui.composable

import android.content.Context
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun VideoPlayer(url: String, context: Context = LocalContext.current) {
    val player = remember { ExoPlayer.Builder(context).build() }

    LaunchedEffect(url) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
    }

    AndroidView(
        factory = {
            WebView(context).apply {
                // Enable JavaScript for the video to work
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                // Set WebViewClient to ensure links open in the WebView and not in a browser
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()

                // Load the YouTube video embed URL
                val htmlContent = """
<!DOCTYPE html>
<html>
<head>
    <title>Video Embed</title>
</head>
<style>
            /* Make iframe container responsive */
            .video-container {
                position: relative;
                width: 100%;
                padding-bottom: 56.25%; /* 16:9 Aspect Ratio */
                height: 0;
                overflow: hidden;
                max-width: 100%;
            }
            .video-container iframe {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
            }
        </style>
<body>
<div class="video-container">
    <iframe src="$url frameborder="0" allow="autoplay; encrypted-media" allowfullscreen"></iframe>
    </div>
</body>
</html>
"""

// In your WebView
                loadData(htmlContent, "text/html", "UTF-8")
                // loadUrl(url)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))

    )
}