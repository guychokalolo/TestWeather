package com.gishokalolo.testexpr.di

import com.gishokalolo.testexpr.data.WeatherRepository
import com.gishokalolo.testexpr.data.WeatherRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface HomeModule {

    @Binds
    @Reusable
    fun provide(repositoryImp: WeatherRepositoryImp): WeatherRepository
}