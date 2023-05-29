package com.gishokalolo.testexpr.data

import com.gishokalolo.testexpr.data.db.model.CityDbModel
import com.gishokalolo.testexpr.domain.model.CityEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun observeCity(location: String): Flow<CityEntity>

    suspend fun requestCity(location: String)

    suspend fun insertDbCity(city: CityDbModel)

    suspend fun getAllFavorites(isFavorite: Boolean): Flow<List<CityEntity>>

    fun observeCities():Flow<List<CityEntity>>
}