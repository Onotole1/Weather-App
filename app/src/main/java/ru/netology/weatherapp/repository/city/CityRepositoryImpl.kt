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
    // Поскольку новые города в НГС добавляются редко,
        // в первую очередь вернём из БД
        cityDao.getCities()
            .map {
                it.toCity()
            }
            .ifEmpty {
                // Если пусто в БД, скачаем с сервера
                citiesApi.getCities()
                    .cities.map(CityEntity::fromCityDto)
                    .also {
                        cityDao.insertCities(it)
                    }
                    .map(CityEntity::toCity)
            }

    override suspend fun selectCity(cityId: Int) {
        cityDao.selectCityById(cityId)
    }

    override fun getSelectedCity(): Flow<City> = cityDao.getSelectedCity().map {
        it?.toCity() ?: City.MOSCOW
    }
}
