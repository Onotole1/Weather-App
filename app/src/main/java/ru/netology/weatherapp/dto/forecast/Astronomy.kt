package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName

data class Astronomy(
    @SerializedName("length_day_human")
    val lengthDayHuman: String,
    @SerializedName("moon_illuminated")
    val moonIlluminated: Int,
    @SerializedName("moon_phase")
    val moonPhase: String,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String
)