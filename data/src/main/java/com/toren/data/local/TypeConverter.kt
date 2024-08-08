package com.toren.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.toren.domain.model.rocket_api.Links
import javax.inject.Inject

@ProvidedTypeConverter
class TypeConverter
@Inject constructor(
    private val gson: Gson
){

    @TypeConverter
    fun fromLinks(links: Links): String {
        return gson.toJson(links)
    }

    @TypeConverter
    fun toLinks(json: String): Links {
        return gson.fromJson(json, Links::class.java)
    }

}