package ru.netology.weatherapp.viewmodel.cities

import ru.netology.weatherapp.model.City
import ru.netology.weatherapp.model.Status

data class CitiesState(
    val query: String = "",
    val cities: List<City> = emptyList(),
    val status: Status<Throwable> = Status.Idle,
) {
    val results = cities.filter {
        it.name.contains(query, true) ||
                it.title.contains(query, true) ||
                it.titleDative?.contains(query) == true ||
                it.titlePrepositional?.contains(query) == true
    }
}
