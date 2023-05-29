package com.gishokalolo.testexpr.data.mapper

import com.gishokalolo.testexpr.data.db.model.CityDbModel
import com.gishokalolo.testexpr.data.remote.response.WeatherResponse
import com.gishokalolo.testexpr.domain.model.CityEntity

fun CityDbModel.toEntity() = CityEntity(
    id = id,
    name = name,
    wind = wind,
    precip = precip,
    temperature = temperature,
    isFavorite = isFavorite,
    image = image
)

fun WeatherResponse.toDbModel() = CityDbModel(
    name = this.location.name,
    wind = this.current.wind_speed,
    precip = this.current.precip,
    temperature = this.current.temperature,
    image = this.current.weather_icon.firstOrNull().orEmpty(),
)

fun CityEntity.toEntityDb() = CityDbModel(
    id = id,
    name = name,
    temperature = temperature,
    wind = wind,
    precip = precip,
    image = image,
    isFavorite = isFavorite
)