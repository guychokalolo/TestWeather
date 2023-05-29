package com.gishokalolo.testexpr.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gishokalolo.testexpr.data.WeatherRepository
import com.gishokalolo.testexpr.data.remote.response.WeatherResponse
import com.gishokalolo.testexpr.domain.model.CityEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewMode(private val repository: WeatherRepository) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("coroutine Exception cause ${exception.cause}")
    }
    private var _cityEntity= MutableLiveData<CityEntity>()
    val cityEntity: LiveData<CityEntity> = _cityEntity

    val _city = MutableLiveData<String?>()

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.requestCity(location = CITY_NAME)
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            repository.observeCity(location = CITY_NAME).collect {
              _cityEntity.value = it
            }
        }
    }

    fun setWeather(id: Long?, city: String?) {
        println("city $city")
        _city.value = city
        viewModelScope.launch {
            if (city != null) {
                repository.observeCity(city).collect {
                    _cityEntity.value =it
                }
            }
        }
    }

    class Factory @Inject constructor(private val repository: WeatherRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewMode(repository = repository) as T
        }
    }

    companion object {
        private const val CITY_NAME = "Paris"
    }
}