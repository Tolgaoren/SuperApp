package com.toren.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toren.domain.model.rocket.Links
import com.toren.domain.model.rocket.Rocket

@Entity(tableName = "rockets")
data class RocketEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date_local") val dateLocal: String?,
    @ColumnInfo(name = "details") val details: String?,
    @ColumnInfo(name = "flight_number") val flightNumber: Int?,
    @ColumnInfo(name = "rocket_id") val rocketId: String?,
    @ColumnInfo(name = "links") val links: Links?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "rocket") val rocket: String?,
    @ColumnInfo(name = "success") val success: Boolean?
)


fun RocketEntity.toRocket(): Rocket {
    return Rocket(
        localId = id,
        dateLocal = dateLocal,
        details = details,
        flightNumber = flightNumber,
        id = rocketId,
        links = links,
        name = name,
        rocket = rocket,
        success = success
    )
}

fun Rocket.toRocketEntity(): RocketEntity {
    return RocketEntity(
        id = localId,
        dateLocal = dateLocal,
        details = details,
        flightNumber = flightNumber,
        rocketId = id,
        links = links,
        name = name,
        rocket = rocket,
        success = success
    )
}
