package ru.netology.weatherapp.repository.city

import kotlinx.coroutines.flow.Flow
import ru.netology.weatherapp.model.City

interface CityRepository {
    suspend fun getCities(): List<City>
    suspend fun getCityById(cityId: Int): City?
    suspend fun selectCity(cityId: Int)
    suspend fun getSelectedCity(): City
    fun observeSelectedCity(): Flow<City>
}
