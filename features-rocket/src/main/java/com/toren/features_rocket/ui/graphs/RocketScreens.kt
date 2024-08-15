package com.toren.features_rocket.ui.graphs

sealed class RocketScreens(
    val route: String,
    val title: String
) {
    object RocketListScreen : RocketScreens(
        route = "RocketListScreen",
        title = "RocketListScreen"
    )
    object RocketDetailScreen : RocketScreens(
        route = "RocketDetailScreen",
        title = "RocketDetailScreen"
    )
    object FavoriteRocketsScreen : RocketScreens(
        route = "FavoriteRocketsScreen",
        title = "FavoriteRocketsScreen"
    )
}