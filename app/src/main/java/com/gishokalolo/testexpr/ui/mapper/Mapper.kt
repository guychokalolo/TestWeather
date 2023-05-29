package com.gishokalolo.testexpr.ui.mapper

import com.gishokalolo.testexpr.domain.model.CityEntity
import com.gishokalolo.testexpr.domain.model.ParamsForAdapter

fun CityEntity.toUiModel() = ParamsForAdapter(
    id = id,
    name = name,
    isFavorite = isFavorite
)
