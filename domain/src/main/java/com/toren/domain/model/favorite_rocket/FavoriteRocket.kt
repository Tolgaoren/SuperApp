package com.toren.domain.model.favorite_rocket

import com.toren.domain.model.rocket_api.Links

data class FavoriteRocket(
    val id: Int = 0,
    val dateLocal: String?,
    val details: String?,
    val flightNumber: Int?,
    val rocketId: String?,
    val links: Links?,
    val name: String?,
    val rocket: String?,
    val success: Boolean?
)
