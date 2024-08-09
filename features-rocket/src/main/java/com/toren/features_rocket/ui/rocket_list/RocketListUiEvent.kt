package com.toren.features_rocket.ui.rocket_list

sealed class RocketListUiEvent {
    object Refresh : RocketListUiEvent()
    data class FavoriteRocket(val rocketId: String) : RocketListUiEvent()

}