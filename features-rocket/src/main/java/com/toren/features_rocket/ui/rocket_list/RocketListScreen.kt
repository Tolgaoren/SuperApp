package com.toren.features_rocket.ui.rocket_list

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.toren.domain.model.rocket.Rocket

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketListScreen(
    viewModel: RocketListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val onRefresh = {
        isRefreshing = true
        viewModel.onEvent(RocketListUiEvent.Refresh)
        isRefreshing = false
    }

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
                state =  state,
                isRefreshing = isRefreshing,
                onRefresh =  onRefresh
            ) {
                LazyColumn (
                    modifier = Modifier.fillMaxWidth()
                ){
                    items(uiState.rockets) { rocket ->
                        RocketItem(rocket = rocket)
                    }
                }
            }
        }
    }
}

@Composable
fun RocketItem(rocket: Rocket) {
    Card (
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = CardDefaults.elevatedShape
    ) {
        Row {
            AsyncImage(
                model = rocket.links?.patch?.small ?:
                    rocket.links?.flickr?.small,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(5.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = rocket.name ?: "No name")
                Text(text = rocket.details ?: "No details")
                Text(text = "Success: ${rocket.success ?: "Unknown"}")
            }
        }
    }
}