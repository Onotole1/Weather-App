package ru.netology.weatherapp.fragment.forecast

import java.time.OffsetDateTime

data class ForecastUiModel(
    val city: String,
    val date: OffsetDateTime,
    val dateFormatted: String,
    val midnightTemperature: String,
    val midnightIcon: String,
    val morningTemperature: String,
    val morningIcon: String,
    val middayTemperature: String,
    val middayIcon: String,
    val eveningTemperature: String,
    val eveningIcon: String,
)
