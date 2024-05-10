package ru.netology.weatherapp.dto.city


import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("cities")
    val cities: List<CityDto>,
)