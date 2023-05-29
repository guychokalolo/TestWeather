package com.gishokalolo.testexpr.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gishokalolo.testexpr.data.db.dao.CityWeatherDao
import com.gishokalolo.testexpr.data.db.model.CityDbModel

@Database(entities = [CityDbModel::class], version = 1)
abstract class WeatherDataBase: RoomDatabase() {

    abstract fun getCityDao(): CityWeatherDao
}