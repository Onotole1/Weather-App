package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("forecasts")
    val forecasts: List<Forecast>,
)