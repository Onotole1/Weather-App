package ru.netology.weatherapp.repository.forecast

import androidx.room.withTransaction
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.netology.weatherapp.api.WeatherApi
import ru.netology.weatherapp.db.WeatherDb
import ru.netology.weatherapp.dto.forecast.Forecast
import ru.netology.weatherapp.dto.forecast.ForecastResponse
import ru.netology.weatherapp.dto.forecast.Hour
import ru.netology.weatherapp.dto.forecast.Links
import ru.netology.weatherapp.entity.forecast.ForecastEntity
import ru.netology.weatherapp.entity.forecast.HourEntity
import ru.netology.weatherapp.entity.forecast.toAstronomy
import ru.netology.weatherapp.entity.forecast.toCloud
import ru.netology.weatherapp.entity.forecast.toForecastValue
import ru.netology.weatherapp.entity.forecast.toPrecipitation
import ru.netology.weatherapp.entity.forecast.toWind
import java.time.OffsetDateTime
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val db: WeatherDb,
    private val api: WeatherApi,
) : ForecastRepository {

    override suspend fun getForecast(city: String, isFresh: Boolean): List<Forecast> {
        if (isFresh) {
            return api.getForecast(city).also {
                writeForecast(it)
            }
                .forecasts
        }

        val forecasts = db.forecastDao.getForecasts(city)

        // Запрашиваем из БД часы для каждого прогноза параллельно
        return coroutineScope {
            forecasts.map {
                async { it.toDto() }
            }
                .awaitAll()
        }
    }

    override suspend fun getForecast(city: String, date: OffsetDateTime): Forecast =
        // Поскольку нет необходимости в сервере, загружаем локальные данные из БД
        db.forecastDao.getForecast(city, date).toDto()

    private suspend fun writeForecast(response: ForecastResponse) {
        // Поскольку используем множество запросов к БД в рамках одной операции,
        // объединим в транзакцию, чтобы либо все запросы прошли, либо ни одного в случае ошибки
        db.withTransaction {
            // Очищаем все старые неактуальные записи
            db.forecastDao.clear()

            response.forecasts.forEach { forecast ->
                // Сначала запишем прогноз, чтобы ForeignKey у часов сработал
                db.forecastDao.insertForecast(ForecastEntity.fromForecast(forecast))

                // Затем запишем каждый час со ссылкой на ForecastEntity
                db.hourDao.insert(
                    forecast.hours.map { hour ->
                        HourEntity.fromHourAndForecast(hour, forecast)
                    }
                )
            }
        }
    }

    private suspend fun ForecastEntity.toDto(): Forecast =
        Forecast(
            astronomy = astronomy.toAstronomy(),
            date = date,
            hours = getHours(this),
            links = Links(city),
        )

    private suspend fun getHours(it: ForecastEntity): List<Hour> =
        db.hourDao.getHours(it).map { hourEntity ->
            with(hourEntity) {
                Hour(
                    cloud = cloud.toCloud(),
                    hour = hour,
                    humidity = humidity.toForecastValue(),
                    icon = icon,
                    iconPath = iconPath,
                    precipitation = precipitation.toPrecipitation(),
                    pressure = pressure.toForecastValue(),
                    temperature = temperature.toForecastValue(),
                    wind = wind.toWind(),
                )
            }
        }
}
