package com.toren.data.remote.dto

import com.toren.domain.model.Rocket

data class RocketDto(
    val auto_update: Boolean,
    val capsules: List<Any>,
    val cores: List<Core>,
    val crew: List<Any>,
    val date_local: String,
    val date_precision: String,
    val date_unix: Int,
    val date_utc: String,
    val details: String,
    val failures: List<Failure>,
    val fairings: Fairings,
    val flight_number: Int,
    val id: String,
    val launch_library_id: Any,
    val launchpad: String,
    val links: LinksDto,
    val name: String,
    val net: Boolean,
    val payloads: List<String>,
    val rocket: String,
    val ships: List<Any>,
    val static_fire_date_unix: Int,
    val static_fire_date_utc: String,
    val success: Boolean,
    val tbd: Boolean,
    val upcoming: Boolean,
    val window: Int
)


fun RocketDto.toRocket(): Rocket {
    return Rocket(
        date_local = date_local,
        details = details,
        flight_number = flight_number,
        id = id,
        links = links.toLinks(),
        name = name,
        rocket = rocket,
        success = success
    )
}