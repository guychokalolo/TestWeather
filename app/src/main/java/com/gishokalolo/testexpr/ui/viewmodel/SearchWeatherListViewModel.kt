package com.gishokalolo.testexpr.ui.viewmodel

import androidx.lifecycle.*
import com.gishokalolo.testexpr.data.WeatherRepository
import com.gishokalolo.testexpr.data.mapper.toEntityDb
import com.gishokalolo.testexpr.domain.model.ParamsForAdapter
import com.gishokalolo.testexpr.domain.state.DataState
import com.gishokalolo.testexpr.ui.mapper.toUiModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchWeatherListViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("coroutine Exception cause ${exception.cause}")
    }

    private val _cityListStateFlow = MutableStateFlow<DataState>(DataState.Empty)
    val cityFavoriteStateFlow: StateFlow<DataState> = _cityListStateFlow

    init {
        viewModelScope.launch {
            _cityListStateFlow.value = DataState.Loading
            val data = repository.observeCities()
            data.catch { error ->
                _cityListStateFlow.value = DataState.Error(error)
            }.collect { list ->
                val dataToUi = list.map { it.toUiModel() }
                _cityListStateFlow.value = DataState.Success(dataToUi)
            }
        }
    }

    fun insertFavorite(params: ParamsForAdapter) {
        viewModelScope.launch {
            repository.observeCity(params.name).collect {
                val res = it.copy(isFavorite = params.isFavorite)
                if (res.name == params.name) {
                    repository.insertDbCity(res.toEntityDb())
                }
            }
        }
    }

    fun setCity(city: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.requestCity(city)
        }
    }

    class Factory @Inject constructor(private val repository: WeatherRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchWeatherListViewModel(repository = repository) as T
        }
    }

}