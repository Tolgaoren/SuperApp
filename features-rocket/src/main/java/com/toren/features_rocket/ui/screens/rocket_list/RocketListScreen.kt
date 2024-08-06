package com.toren.features_rocket.ui.screens.rocket_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RocketListScreen(
    viewModel: RocketListViewModel = hiltViewModel()
) {
    viewModel.test()
    NavBox(text = "Rockets")
}


@Composable
fun NavBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}