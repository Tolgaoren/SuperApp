package com.toren.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toren.domain.model.favorite_rocket.FavoriteRocket

@Entity(tableName = "rockets")
data class FavoriteRocketEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "rocket_id") val name: String
)


fun FavoriteRocketEntity.toFavoriteRocket(): FavoriteRocket {
    return FavoriteRocket(
        id = id,
        name = name
    )
}

fun FavoriteRocket.toFavoriteRocketEntity(): FavoriteRocketEntity {
    return FavoriteRocketEntity(
        id = id,
        name = name
    )
}