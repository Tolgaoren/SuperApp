package com.toren.features_rocket.ui.rocket_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RocketDetailScreen(modifier: Modifier = Modifier, rocketId: Int) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Rocket Detail Screen for Rocket ID: $rocketId")
    }
}
