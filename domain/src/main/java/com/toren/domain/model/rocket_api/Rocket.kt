package com.toren.domain.model.rocket_api

data class Rocket(
    val dateLocal: String?,
    val details: String?,
    val flightNumber: Int?,
    val id: String?,
    val links: Links?,
    val name: String?,
    val rocket: String?,
    val success: Boolean?
)