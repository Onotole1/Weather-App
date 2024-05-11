package ru.netology.weatherapp.viewmodel.forecast

import ru.netology.weatherapp.dto.forecast.Forecast
import ru.netology.weatherapp.model.Status

data class ForecastState(
    val forecast: List<Forecast> = emptyList(),
    val status: Status<Throwable> = Status.Idle,
) {
    private val isError = status.error != null
    val isEmptyLoading = status is Status.Loading && forecast.isEmpty()
    val isRefreshing = status is Status.Loading && forecast.isNotEmpty()
    val isEmptyError = isError && forecast.isEmpty()
    val notEmptyError = isError && forecast.isNotEmpty()
}
