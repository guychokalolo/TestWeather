package com.gishokalolo.testexpr

import android.app.Application
import com.gishokalolo.testexpr.data.db.WeatherDataBase
import com.gishokalolo.testexpr.di.AppModule
import com.gishokalolo.testexpr.di.ApplicationComponent
import com.gishokalolo.testexpr.di.DaggerApplicationComponent

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    var component: ApplicationComponent? = null
    get() {
        if (field == null) {
            field = DaggerApplicationComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        }
        return field
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}