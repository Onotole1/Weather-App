package ru.netology.weatherapp.dto.city

import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_dative")
    val titleDative: String?,
    @SerializedName("title_prepositional")
    val titlePrepositional: String?,
)
