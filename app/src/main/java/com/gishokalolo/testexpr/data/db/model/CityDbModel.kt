package com.gishokalolo.testexpr.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    var id: Long = 0,
    @ColumnInfo(name= "name")
    var name: String,
    @ColumnInfo(name= "temperature")
    var temperature: Int,
    @ColumnInfo(name= "wind")
    var wind: Int,
    @ColumnInfo(name= "precip")
    var precip: Double,
    @ColumnInfo(name= "image")
    var image: String,
    @ColumnInfo(name= "isFavorite")
    var isFavorite: Boolean = false
)
