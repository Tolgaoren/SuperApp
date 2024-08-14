package com.toren.features_rocket.ui.rocket_detail

sealed class RocketDetailUiEvent {
    data class FavoriteRocket(val rocketId: String) : RocketDetailUiEvent()
}