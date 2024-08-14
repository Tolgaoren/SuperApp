package com.toren.features_rocket.ui.rocket_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.toren.domain.model.rocket.Rocket
import com.toren.features_rocket.ui.graphs.RocketScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketListScreen(
    viewModel: RocketListViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val onRefresh = {
        isRefreshing = true
        viewModel.onEvent(RocketListUiEvent.Refresh)
        isRefreshing = false
    }
    val favoriteRocketIds by viewModel.favoriteRocketIds.collectAsState()
    val isDataSavedLocally by viewModel.isDataSavedLocally.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
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
            PullToRefreshBox(
                state = state,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.rockets) { rocket ->
                        RocketItem(
                            rocket = rocket,
                            isFavorite = favoriteRocketIds.contains(rocket.id!!),
                            onFavoriteClick = {
                                viewModel.onEvent(
                                    RocketListUiEvent.FavoriteRocket(rocket.id!!)
                                )
                            },
                            onClick = {
                                if (isDataSavedLocally) {
                                    navController.navigate(
                                        RocketScreens.RocketDetailScreen.route
                                                + "/${rocket.id}"
                                    )
                                } else {
                                    viewModel.onEvent(RocketListUiEvent.Refresh)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RocketItem(
    rocket: Rocket,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = CardDefaults.elevatedShape,
        onClick = onClick
    ) {
        Row {
            AsyncImage(
                model = rocket.links?.patch?.small ?: rocket.links?.flickr?.small,
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .width(100.dp)
                    .padding(top = 20.dp, bottom = 20.dp, start = 10.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = rocket.name ?: "No name",
                    style = TextStyle(
                        fontSize = TextUnit(17f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = rocket.details ?: "No details",
                    style = TextStyle(
                        fontSize = TextUnit(14f, TextUnitType.Sp)
                    ),
                    modifier = Modifier.padding(top = 5.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
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
                    IconButton(
                        onClick = onFavoriteClick
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite button",
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }

            }
        }
    }
}