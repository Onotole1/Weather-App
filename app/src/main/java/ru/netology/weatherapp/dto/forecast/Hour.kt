package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("cloud")
    val cloud: Cloud,
    @SerializedName("hour")
    val hour: Int,
    @SerializedName("humidity")
    val humidity: ForecastValue,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("icon_path")
    val iconPath: String,
    @SerializedName("precipitation")
    val precipitation: Precipitation,
    @SerializedName("pressure")
    val pressure: ForecastValue,
    @SerializedName("temperature")
    val temperature: ForecastValue,
    @SerializedName("wind")
    val wind: Wind
)