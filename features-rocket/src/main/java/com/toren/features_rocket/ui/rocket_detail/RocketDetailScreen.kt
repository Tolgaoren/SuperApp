package com.toren.features_rocket.ui.rocket_detail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.toren.domain.model.rocket.Rocket

@Composable
fun RocketDetailScreen(
    rocketId: String,
    viewModel: RocketDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(rocketId) {
        viewModel.getRocketDetail(rocketId)
        viewModel.getFavoriteStatus(rocketId)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        if (uiState.isLoading) {
            Log.d("RocketDetailScreen", "Loading rocket detail...")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error.toString())
            }
        } else if (uiState.rocket != null) {
            Log.d("RocketDetailScreen", "Rocket detail loaded: ${uiState.rocket}")
            RocketDetail(rocket = uiState.rocket!!,
                isFavorite = isFavorite,
                onFavoriteClick = {
                    viewModel.onEvent(
                        RocketDetailUiEvent.FavoriteRocket(rocketId)
                    )
                }
            )
        }
    }
}

@Composable
fun RocketDetail(
    rocket: Rocket,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit = {},
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(10.dp)
    ) {

        AsyncImage(
            model = if (rocket.links?.flickr?.original?.size!! > 0) {
                rocket.links?.flickr?.original!![0]
            } else if (!rocket.links?.flickr?.small.isNullOrEmpty()) {
                rocket.links?.flickr?.small
            } else if (!rocket.links?.patch?.large.isNullOrEmpty()) {
                rocket.links?.patch?.large
            } else {
                rocket.links?.patch?.small
            },
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rocket.name ?: "No name",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.wrapContentWidth()
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite
                    else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite button",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
        Text(
            text = rocket.details ?: "No details",
            style = TextStyle(
                fontSize = TextUnit(15f, TextUnitType.Sp)
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row {
                Text(
                    text = "Success: ",
                    style = TextStyle(
                        fontSize = TextUnit(14f, TextUnitType.Sp)
                    )
                )
                Text(
                    text = rocket.success?.toString() ?: "Unknown",
                    style = TextStyle(
                        fontSize = TextUnit(14f, TextUnitType.Sp),
                        color = rocket.success?.let {
                            if (it) Color.Green else Color.Red
                        } ?: Color.Gray
                    )
                )
            }
            Text(
                text = rocket.dateLocal.toString(),
                style = TextStyle(
                    fontSize = TextUnit(14f, TextUnitType.Sp)
                ),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}