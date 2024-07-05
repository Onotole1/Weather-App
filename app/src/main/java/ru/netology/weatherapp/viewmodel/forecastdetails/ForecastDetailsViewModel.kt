package ru.netology.weatherapp.viewmodel.forecastdetails

import kotlinx.coroutines.flow.StateFlow
import ru.netology.weatherapp.dto.forecast.Forecast

interface ForecastDetailsViewModel {
    val forecast: StateFlow<Forecast?>
}