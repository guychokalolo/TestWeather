package com.gishokalolo.testexpr.data

import com.gishokalolo.testexpr.data.db.dao.CityWeatherDao
import com.gishokalolo.testexpr.data.db.model.CityDbModel
import com.gishokalolo.testexpr.data.mapper.toDbModel
import com.gishokalolo.testexpr.data.mapper.toEntity
import com.gishokalolo.testexpr.data.network.WeatherApi
import com.gishokalolo.testexpr.data.remote.response.WeatherResponse
import com.gishokalolo.testexpr.domain.model.CityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val api: WeatherApi,
    private val dao: CityWeatherDao
) : WeatherRepository {

    override fun observeCity(name: String): Flow<CityEntity> {
        return dao.getCityByName(name = name).filterNotNull().map { it.toEntity() }
    }

    override suspend fun requestCity(location: String) {
        val city = api.getCurrentWeather(location = location)
        dao.insertOrUpdate(city.toDbModel())
    }

    override suspend fun insertDbCity(city: CityDbModel) {
        dao.insertOrUpdate(city)
    }

    override suspend fun getAllFavorites(isFavorite: Boolean): Flow<List<CityEntity>> = flow {
        dao.getAllFavorites(isFavorite = isFavorite).collect { list ->
            emit(list.map { it.toEntity() })
        }
    }.flowOn(Dispatchers.IO)

    override fun observeCities(): Flow<List<CityEntity>> {
        return dao.getAll().filterNotNull().map { list ->
            list.map { it.toEntity() }
        }
    }

}