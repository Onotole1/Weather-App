package ru.netology.weatherapp.repository.forecast

import ru.netology.weatherapp.dto.forecast.Forecast
import java.time.OffsetDateTime

interface ForecastRepository {
    suspend fun getForecast(city: String, isFresh: Boolean): List<Forecast>
    suspend fun getForecast(city: String, date: OffsetDateTime): Forecast
}
