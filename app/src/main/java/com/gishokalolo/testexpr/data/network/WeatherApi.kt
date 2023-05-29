package com.gishokalolo.testexpr.data.network

import com.gishokalolo.testexpr.data.remote.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current")
    suspend fun getCurrentWeather(
        @Query("query") location: String
    ): WeatherResponse
}