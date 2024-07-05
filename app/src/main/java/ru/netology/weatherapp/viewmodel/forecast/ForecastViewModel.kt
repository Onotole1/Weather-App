package ru.netology.weatherapp.viewmodel.forecast

import kotlinx.coroutines.flow.StateFlow

interface ForecastViewModel {
    val state: StateFlow<ForecastState>

    fun loadForecast()
    fun handleError()
}