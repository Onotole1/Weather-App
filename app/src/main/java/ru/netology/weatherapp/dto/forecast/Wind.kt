package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("direction")
    val direction: Direction,
    @SerializedName("speed")
    val speed: ForecastValue
)