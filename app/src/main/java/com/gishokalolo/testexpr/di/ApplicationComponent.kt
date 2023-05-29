package com.gishokalolo.testexpr.di

import com.gishokalolo.testexpr.ui.view.MainActivity
import com.gishokalolo.testexpr.ui.view.FavoriteFragment
import com.gishokalolo.testexpr.ui.view.SearchWeatherFragment
import com.gishokalolo.testexpr.ui.view.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, HomeModule::class])
@Singleton
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(weatherList: SearchWeatherFragment)
}