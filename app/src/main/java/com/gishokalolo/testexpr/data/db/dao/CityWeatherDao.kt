package com.gishokalolo.testexpr.data.db.dao

import androidx.room.*
import com.gishokalolo.testexpr.data.db.model.CityDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CityWeatherDao {

    @Query("SELECT * FROM city ORDER BY id ASC")
    fun getAll(): Flow<List<CityDbModel>>

    @Query("SELECT * FROM city WHERE isFavorite = :isFavorite")
    fun getAllFavorites(isFavorite: Boolean): Flow<List<CityDbModel>>

    @Query("SELECT * FROM city WHERE name = :name")
    fun getCityByName(name: String): Flow<CityDbModel?>

    @Query("SELECT * FROM city WHERE name = :name")
    suspend fun getCityByNameCheckId(name: String): CityDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDbCity(city: CityDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDbCurrent(city: List<CityDbModel>)

    @Transaction()
    suspend fun insertOrUpdate(city: CityDbModel) {
        val id = getCityByNameCheckId(city.name)?.id
        if (id == null) {
            insertDbCity(city)
        } else {
            insertDbCity(city.copy(id = id))
        }
    }
}