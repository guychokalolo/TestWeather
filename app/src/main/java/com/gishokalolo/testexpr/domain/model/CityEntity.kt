package com.gishokalolo.testexpr.domain.model



data class CityEntity(
    val id: Long,
    val name: String,
    val temperature: Int,
    val wind: Int,
    val precip: Double,
    val image: String,
    val isFavorite: Boolean
)
