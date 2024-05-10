package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName

data class Direction(
    @SerializedName("title")
    val title: String,
    @SerializedName("title_letter")
    val titleLetter: String,
    @SerializedName("title_short")
    val titleShort: String,
    @SerializedName("value")
    val value: String
)