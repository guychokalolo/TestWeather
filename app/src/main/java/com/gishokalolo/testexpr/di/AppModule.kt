package com.gishokalolo.testexpr.di

import androidx.room.Room
import com.gishokalolo.testexpr.App
import com.gishokalolo.testexpr.data.db.WeatherDataBase
import com.gishokalolo.testexpr.data.db.dao.CityWeatherDao
import com.gishokalolo.testexpr.data.network.WeatherApi
import com.gishokalolo.testexpr.data.network.WeatherInterceptor
import com.gishokalolo.testexpr.utils.Constants
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Provides
    @Singleton
    internal fun provideApplicationContext() = application

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
            .setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
            )
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun httpLoginInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun httpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(WeatherInterceptor(application))
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideApi(gson: Gson, okHttpClient: OkHttpClient): WeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_NEW_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
        return retrofit.build().create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomDb(): WeatherDataBase =
        Room.databaseBuilder(application, WeatherDataBase::class.java, "weather").build()


    @Provides
    @Singleton
    fun provideCityDao(weatherDataBase: WeatherDataBase): CityWeatherDao {
        return weatherDataBase.getCityDao()
    }
}