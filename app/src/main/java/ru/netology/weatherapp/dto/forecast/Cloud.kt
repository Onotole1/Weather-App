package ru.netology.weatherapp.dto.forecast


import com.google.gson.annotations.SerializedName

data class Cloud(
    @SerializedName("title")
    val title: String,
    @SerializedName("value")
    val value: String
)