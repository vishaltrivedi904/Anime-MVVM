package com.example.animeapplication.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.animeapplication.domain.model.Anime

@Composable
fun AnimeCard(anime: Anime, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            Box(modifier = Modifier.height(160.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(anime.imageUrl),
                    contentDescription = anime.title,
                    modifier = Modifier.fillMaxWidth().height(160.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
                )

                anime.score?.let {
                    Row(
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                            .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, null, Modifier.size(12.dp), tint = Color(0xFFFFC107))
                        Spacer(Modifier.width(2.dp))
                        Text(anime.scoreText, style = MaterialTheme.typography.labelSmall, 
                            color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().height(40.dp).align(Alignment.BottomCenter)
                        .background(Brush.verticalGradient(listOf(Color.Transparent, MaterialTheme.colorScheme.surfaceVariant)))
                )
            }

            Column(Modifier.padding(12.dp)) {
                Text(anime.displayTitle, style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Text("${anime.episodesText} eps • ${anime.year ?: ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
