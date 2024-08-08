package com.toren.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toren.domain.model.favorite_rocket.FavoriteRocket
import com.toren.domain.model.rocket_api.Links

@Entity(tableName = "rockets")
data class FavoriteRocketEntity(
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


fun FavoriteRocketEntity.toFavoriteRocket(): FavoriteRocket {
    return FavoriteRocket(
        id = id,
        dateLocal = dateLocal,
        details = details,
        flightNumber = flightNumber,
        rocketId = rocketId,
        links = links,
        name = name,
        rocket = rocket,
        success = success
    )
}

fun FavoriteRocket.toFavoriteRocketEntity(): FavoriteRocketEntity {
    return FavoriteRocketEntity(
        id = id,
        dateLocal = dateLocal,
        details = details,
        flightNumber = flightNumber,
        rocketId = rocketId,
        links = links,
        name = name,
        rocket = rocket,
        success = success
    )
}