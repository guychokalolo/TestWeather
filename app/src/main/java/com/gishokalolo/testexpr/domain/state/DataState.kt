package com.gishokalolo.testexpr.domain.state

import com.gishokalolo.testexpr.domain.model.ParamsForAdapter

sealed class DataState {
    object Loading : DataState()
    class Success(val data: List<ParamsForAdapter>): DataState()
    class Error(val message: Throwable): DataState()
    object Empty: DataState()
}
