package ru.netology.weatherapp.api

import retrofit2.http.GET
import ru.netology.weatherapp.dto.city.CityResponse

interface CitiesApi {
    @GET("cities")
    suspend fun getCities(): CityResponse
}
