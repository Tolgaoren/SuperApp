package com.toren.features_rocket.ui.favorite_rockets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.toren.features_rocket.ui.graphs.RocketScreens
import com.toren.features_rocket.ui.rocket_list.RocketItem

@Composable
fun FavoriteRocketsScreen(
    viewModel: FavoriteRocketsViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(FavoriteRocketsUiEvent.Refresh)
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column {

            Text(
                text = "Favorites",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,

                modifier = Modifier
                    .padding(
                        start = 30.dp,
                        top = 10.dp,
                        bottom = 5.dp
                    )
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = uiState.error.toString())
                }
            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.rockets) { rocket ->
                        RocketItem(
                            rocket = rocket,
                            isFavorite = uiState.rockets.contains(rocket),
                            onFavoriteClick = {
                                viewModel.onEvent(
                                    FavoriteRocketsUiEvent.FavoriteRocket(rocket)
                                )
                            },
                            onClick = {
                                navController.navigate(
                                    RocketScreens
                                        .RocketDetailScreen
                                        .route + "/${rocket.id}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}