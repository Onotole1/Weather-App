package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("city")
    val city: String
)