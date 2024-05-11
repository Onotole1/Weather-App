package ru.netology.weatherapp.repository.forecast

import ru.netology.weatherapp.dto.forecast.Forecast
import java.time.OffsetDateTime

interface ForecastRepository {
    /**
     * Запрос списка прогнозов погоды
     * @param city - город, для которого ищем погоду
     * @param isFresh – нужно ли возвращать данные с сервера или достаточно кешированной версии
     */
    suspend fun getForecast(city: String, isFresh: Boolean): List<Forecast>

    /**
     * Запрос прогноза погоды на 1 день
     * @param city - город, для которого ищем погоду
     * @param date – дата прогноза
     */
    suspend fun getForecast(city: String, date: OffsetDateTime): Forecast
}
