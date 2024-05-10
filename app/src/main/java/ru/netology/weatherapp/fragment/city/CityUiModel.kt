package ru.netology.weatherapp.fragment.city

import io.mcarle.konvert.api.KonvertFrom
import ru.netology.weatherapp.model.City

data class CityUiModel(
    val id: Int,
    val title: String,
    val selected: Boolean = false,
) {
    data class Payload(
        val selected: Boolean? = null,
    )

    @KonvertFrom(City::class)
    companion object
}
