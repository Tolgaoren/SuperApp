package com.toren.data.remote.dto

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.toren.domain.model.rocket.Rocket
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

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
        localId = 0,
        dateLocal = date_local.toDate(),
        details = details,
        flightNumber = flight_number,
        id = id,
        links = links.toLinks(),
        name = name,
        rocket = rocket,
        success = success
    )
}

fun String.toDate(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val inputFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val outputFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        val offsetDateTime: OffsetDateTime = OffsetDateTime.parse(this, inputFormatter)
        offsetDateTime.format(outputFormatter)
    } else {
        try {
            val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
            val outputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date = inputFormatter.parse(this) ?: return this
            outputFormatter.format(date)
        } catch (e: Exception) {
            Log.e("toDate", e.message.toString())
            this
        }
    }
}