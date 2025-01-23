package com.example.animeapplication.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.animeapplication.R
import com.example.animeapplication.data.api.NetworkResponse
import com.example.animeapplication.data.model.details.AnimeDetails
import com.example.animeapplication.ui.composable.VideoPlayer
import com.example.animeapplication.ui.theme.PrimaryColor
import com.example.animeapplication.ui.theme.roboto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(navController: NavController, animeId: Int) {
    val mViewModel: AnimeDetailViewModel = hiltViewModel()

    val onBackPress: () -> Unit = {
        navController.navigateUp()
    }

    LaunchedEffect(Unit) {
        mViewModel.fetchAnimeDetails(animeId)
    }

    val animeDetail by mViewModel.animeDetail.observeAsState(initial = NetworkResponse.Loading)


    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(PrimaryColor), topBar = {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = { onBackPress() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = PrimaryColor),
            modifier = Modifier.background(
                PrimaryColor
            ),
            title = {
                Image(
                    modifier = Modifier.height(40.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "logo"
                )
            })
    }, content = { contentPadding ->

        Box(
            modifier = Modifier
                .padding(contentPadding)
                .background(PrimaryColor)
        )
        {

            when (animeDetail) {

                NetworkResponse.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                is NetworkResponse.Success -> {

                    (animeDetail as NetworkResponse.Success<AnimeDetails>).data.let {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            if (it.data.trailer.url != null) {
                                VideoPlayer(url = it.data.trailer.embed_url)
                            } else {
                                Image(
                                    painter = rememberAsyncImagePainter(it.data.images.jpg.image_url),
                                    contentDescription = it.data.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                color = Color.White,
                                text = it.data.title,
                                fontSize = 18.sp,
                                fontFamily = roboto,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                fontFamily = roboto,
                                fontWeight = FontWeight.Normal,
                                color = Color.Yellow,
                                fontSize = 14.sp,
                                text = "Genres: ${it.data.genres.joinToString { genre -> genre.name }}"
                            )
                            Spacer(modifier = Modifier.size(10.dp))

                            Row(modifier = Modifier.fillMaxWidth()){
                                Text(
                                    fontFamily = roboto,
                                    fontWeight = FontWeight.Light,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    text = "Episodes: ${it.data.episodes}",
                                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color.Red).padding(4.dp)
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(
                                    fontFamily = roboto,
                                    fontWeight = FontWeight.Light,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    text = "Rating: ${it.data.score}",
                                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color.Red).padding(4.dp))
                            }
                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                fontFamily = roboto,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                fontSize = 14.sp,
                                text = it.data.synopsis
                            )
                        }
                    }

                }

                is NetworkResponse.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error: ${(animeDetail as NetworkResponse.Error).message}")
                    }

                }

            }

        }
    })

}