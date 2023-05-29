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
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _cityFavoriteStateFlow = MutableStateFlow<DataState>(DataState.Empty)
    val cityFavoriteStateFlow: StateFlow<DataState> = _cityFavoriteStateFlow

    init {
        viewModelScope.launch {
            _cityFavoriteStateFlow.value = DataState.Loading
            val data = repository.getAllFavorites(true)
            data.catch { error ->
                _cityFavoriteStateFlow.value = DataState.Error(error)
            }.collect { list ->
                val dataToUi = list.map { it.toUiModel() }
                _cityFavoriteStateFlow.value = DataState.Success(dataToUi)
            }
        }
    }

    fun deleteFavorite(location: String) {
        viewModelScope.launch {
            repository.observeCity(location).collect {
                val res = it.copy(isFavorite = false)
                if (!res.isFavorite) {
                    repository.insertDbCity(res.toEntityDb())
                }
            }
        }
    }

    class Factory @Inject constructor(private val repository: WeatherRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoriteViewModel(repository = repository) as T
        }
    }
}