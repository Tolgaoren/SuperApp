package com.toren.features_rocket.ui.favorite_rockets

import com.toren.domain.model.rocket.Rocket

data class FavoriteRocketsUiState(
    val favoriteRockets: List<Rocket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)