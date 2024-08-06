package com.toren.data.remote.api

import com.toren.data.remote.dto.RocketDto
import retrofit2.http.GET

interface RocketApi {

    @GET("launches")
    suspend fun getLaunches(): List<RocketDto>

}