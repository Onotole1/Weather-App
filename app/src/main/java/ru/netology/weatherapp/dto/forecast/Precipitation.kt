package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName

data class Precipitation(
    @SerializedName("title")
    val title: String,
    @SerializedName("value")
    val value: String
)