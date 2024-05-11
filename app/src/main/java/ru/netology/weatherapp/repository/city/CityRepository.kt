package ru.netology.weatherapp.repository.city

import kotlinx.coroutines.flow.Flow
import ru.netology.weatherapp.model.City

interface CityRepository {
    /**
     * Запрос всех городов
     */
    suspend fun getCities(): List<City>

    /**
     * Выбрать город в качестве текущего
     */
    suspend fun selectCity(cityId: Int)

    /**
     * Запрос текущего города
     */
    fun getSelectedCity(): Flow<City>
}
