package com.toren.features_rocket.ui.favorite_rockets

import com.toren.domain.model.rocket.Rocket

sealed class FavoriteRocketsUiEvent {
    data class FavoriteRocket(val rocket: Rocket) : FavoriteRocketsUiEvent()
    object Refresh : FavoriteRocketsUiEvent()
}