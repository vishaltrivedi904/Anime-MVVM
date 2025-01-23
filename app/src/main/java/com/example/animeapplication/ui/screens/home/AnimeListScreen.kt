package com.example.animeapplication.ui.screens.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.animeapplication.R
import com.example.animeapplication.data.model.home.Data
import com.example.animeapplication.ui.theme.PrimaryColor
import com.example.animeapplication.ui.theme.SecondaryColor
import com.example.animeapplication.ui.theme.roboto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(onAnimeClick: (Int) -> Unit) {

    val viewModel: AnimeViewModel = hiltViewModel()


    LaunchedEffect(Unit) {
        viewModel.fetchTopAnime()
    }
    val animeList: LazyPagingItems<Data> = viewModel.animeList.collectAsLazyPagingItems()

    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PrimaryColor),
                title = {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "logo",
                        modifier = Modifier.height(40.dp)
                    )
                })
        },
        content = { contentPadding ->
            when (val loadState = animeList.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryColor)
                            .padding(contentPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                is LoadState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryColor)
                            .padding(contentPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = loadState.error.localizedMessage ?: "Unknown error")
                    }

                }

                is LoadState.NotLoading -> {
                    Box(
                        modifier = Modifier
                            .background(PrimaryColor)
                            .padding(contentPadding)
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp), // Padding around the grid
                            verticalArrangement = Arrangement.spacedBy(8.dp), // Vertical spacing between grid items
                        ) {
                            items(animeList.itemCount) { animeIndex ->
                                val anime = animeList[animeIndex]

                                // Animated visibility for smooth transitions
                                AnimatedVisibility(visible = true) {
                                    // Each grid item content
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .clickable { onAnimeClick(anime!!.mal_id) }
                                            .background(SecondaryColor)
                                            .padding(8.dp)
                                    ) {
                                        // Anime image
                                        Image(
                                            painter = rememberAsyncImagePainter(anime!!.images.jpg.image_url),
                                            contentDescription = anime.title,
                                            modifier = Modifier
                                                .fillMaxWidth() // Take full width
                                                .height(150.dp)
                                                .clip(RoundedCornerShape(8.dp)), // Fixed height for image
                                            contentScale = ContentScale.Crop,
                                        )

                                        Spacer(modifier = Modifier.height(8.dp)) // Vertical spacer between image and text

                                        // Anime text details
                                        Row(modifier = Modifier.fillMaxSize()) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                                    .weight(1f)
                                            ) {
                                                Text(
                                                    text = anime.title,
                                                    maxLines = 1,
                                                    textAlign = TextAlign.Start,
                                                    overflow = TextOverflow.Ellipsis,
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,
                                                    fontFamily = roboto
                                                )
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(SecondaryColor)
                                                ) {
                                                    Text(
                                                        text = "Episodes: ${anime.episodes}",
                                                        color = Color.White,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Light,
                                                        fontFamily = roboto
                                                    )
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Text(
                                                        text = "Rating: ${anime.score}",
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Light,
                                                        fontSize = 12.sp,
                                                        fontFamily = roboto
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            // Box to wrap the button and allow full height
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .align(Alignment.Bottom)
                                                    .weight(0.45f)  // Give it weight to take up available space
                                            ) {
                                                Button(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(30.dp)
                                                        .align(Alignment.BottomCenter),  // Align button at the bottom of the Box
                                                    shape = RoundedCornerShape(4.dp),
                                                    onClick = { onAnimeClick(anime.mal_id) },
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = Color.Red,
                                                        contentColor = Color.White
                                                    ),
                                                    content = {
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            modifier = Modifier.fillMaxWidth()
                                                        ) {
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(22.dp)
                                                                    .padding(0.dp),
                                                                imageVector = Icons.Default.PlayArrow,
                                                                contentDescription = stringResource(
                                                                    R.string.view
                                                                ),
                                                                tint = Color.White,
                                                            )
                                                            Text(
                                                                textAlign = TextAlign.Center,
                                                                text = stringResource(R.string.view),
                                                                fontSize = 12.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color.White,
                                                                fontFamily = roboto,
                                                                style = TextStyle(
                                                                    platformStyle = PlatformTextStyle(
                                                                        includeFontPadding = false
                                                                    )
                                                                ),
                                                            )
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }


        })

}




