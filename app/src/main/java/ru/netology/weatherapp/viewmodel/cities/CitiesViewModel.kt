package ru.netology.weatherapp.viewmodel.cities

import kotlinx.coroutines.flow.StateFlow

interface CitiesViewModel {
    val state: StateFlow<CitiesState>

    fun search(query: String) = Unit
    fun loadCities() = Unit
    fun setCity(id: Int) = Unit
}