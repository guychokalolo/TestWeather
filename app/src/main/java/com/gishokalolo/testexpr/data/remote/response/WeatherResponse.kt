package com.gishokalolo.testexpr.data.remote.response

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("request")
    val request: WeatherRequest,
    @SerializedName("location")
    val location: WeatherLocation,
    @SerializedName("current")
    val current: CurrentWeather
) {
    data class WeatherRequest(
        @SerializedName("type")
        val type: String,
        @SerializedName("query")
        val query: String,
        @SerializedName("language")
        val language: String,
        @SerializedName("unit")
        val unit: String
    )

    data class WeatherLocation(
        @SerializedName("name")
        val name: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: String,
        @SerializedName("timezone_id")
        val timezone_id: String,
        @SerializedName("localtime")
        val localtime: String,
        @SerializedName("localtime_epoch")
        val localtime_epoch: Int,
        @SerializedName("utc_offset")
        val utc_offset: String
    )

    data class CurrentWeather(
        @SerializedName("observation_time")
        val observation_time: String,
        @SerializedName("temperature")
        val temperature: Int,
        @SerializedName("weather_code")
        val weather_code: Int,
        @SerializedName("weather_icons")
        val weather_icon: List<String> = ArrayList(),
        @SerializedName("weather_description")
        val weather_description: List<String> = ArrayList(),
        @SerializedName("wind_speed")
        val wind_speed: Int,
        @SerializedName("wind_degree")
        val wind_degree: Int,
        @SerializedName("wind_dir")
        val wind_dir: String,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("precip")
        val precip: Double,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("cloudcover")
        val cloudcover: Int,
        @SerializedName("feelslike")
        val feelslike: Int,
        @SerializedName("uv_index")
        val uv_index: Int,
        @SerializedName("visibility")
        val visibility: Int,
        @SerializedName("is_day")
        val is_day: String
    )
}
