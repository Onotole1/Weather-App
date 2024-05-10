package ru.netology.weatherapp.repository.city

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.netology.weatherapp.api.CitiesApi
import ru.netology.weatherapp.dao.city.CityDao
import ru.netology.weatherapp.entity.city.CityEntity
import ru.netology.weatherapp.entity.city.fromCityDto
import ru.netology.weatherapp.entity.city.toCity
import ru.netology.weatherapp.model.City
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val citiesApi: CitiesApi,
) : CityRepository {
    override suspend fun getCities(): List<City> =
        cityDao.getCities()
            .map {
                it.toCity()
            }
            .ifEmpty {
                citiesApi.getCities()
                    .cities.map(CityEntity::fromCityDto)
                    .also {
                        cityDao.insertCities(it)
                    }
                    .map(CityEntity::toCity)
            }

    override suspend fun getCityById(cityId: Int): City? = cityDao.getCityById(cityId)?.toCity()

    override suspend fun selectCity(cityId: Int) {
        cityDao.selectCityById(cityId)
    }

    override suspend fun getSelectedCity(): City =
        cityDao.getSelectedCity()?.toCity() ?: City.MOSCOW

    override fun observeSelectedCity(): Flow<City> = cityDao.observeSelectedCity().map {
        it?.toCity() ?: City.MOSCOW
    }
}
