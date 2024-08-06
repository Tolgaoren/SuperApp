package com.toren.domain.model

data class Rocket(
    val date_local: String,
    val details: String,
    val flight_number: Int,
    val id: String,
    val links: Links,
    val name: String,
    val rocket: String,
    val success: Boolean
)