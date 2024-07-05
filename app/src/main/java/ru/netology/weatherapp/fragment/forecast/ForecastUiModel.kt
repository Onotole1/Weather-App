package ru.netology.weatherapp.fragment.forecast

import java.time.OffsetDateTime

data class ForecastUiModel(
    val city: String,
    val date: OffsetDateTime,
    val dateFormatted: String,
    val midnightTemperature: String,
    val morningTemperature: String,
    val middayTemperature: String,
    val eveningTemperature: String,
    val midnightIcon: String = "",
    val morningIcon: String = "",
    val middayIcon: String = "",
    val eveningIcon: String = "",
)
