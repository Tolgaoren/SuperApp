package com.toren.domain.model.rocket

data class Rocket(
    val localId: Int,
    val dateLocal: String?,
    val details: String?,
    val flightNumber: Int?,
    val id: String?,
    val links: Links?,
    val name: String?,
    val rocket: String?,
    val success: Boolean?
)