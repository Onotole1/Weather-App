package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName

data class ForecastValue(
    @SerializedName("avg")
    val avg: Int,
    @SerializedName("max")
    val max: Int,
    @SerializedName("min")
    val min: Int
)