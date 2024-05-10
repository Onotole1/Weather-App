package ru.netology.weatherapp.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.netology.weatherapp.dto.forecast.ForecastResponse

interface WeatherApi {
    @GET("forecasts/forecast")
    suspend fun getForecast(@Query("city") city: String): ForecastResponse
}
