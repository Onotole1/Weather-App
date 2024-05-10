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
import ru.netology.weatherapp.entity.forecast.AstronomyEmbedded
import ru.netology.weatherapp.entity.forecast.CloudEmbedded
import ru.netology.weatherapp.entity.forecast.ForecastEntity
import ru.netology.weatherapp.entity.forecast.ForecastValueEmbedded
import ru.netology.weatherapp.entity.forecast.HourEntity
import ru.netology.weatherapp.entity.forecast.PrecipitationEmbedded
import ru.netology.weatherapp.entity.forecast.WindEmbedded
import ru.netology.weatherapp.entity.forecast.fromAstronomy
import ru.netology.weatherapp.entity.forecast.fromCloud
import ru.netology.weatherapp.entity.forecast.fromForecastValue
import ru.netology.weatherapp.entity.forecast.fromPrecipitation
import ru.netology.weatherapp.entity.forecast.fromWind
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

        return coroutineScope {
            forecasts.map {
                async { it.toDto() }
            }
                .awaitAll()
        }
    }

    override suspend fun getForecast(city: String, date: OffsetDateTime): Forecast =
        db.forecastDao.getForecast(city, date).toDto()

    private suspend fun writeForecast(forecast: ForecastResponse) {
        db.withTransaction {
            db.forecastDao.clear()

            forecast.forecasts.forEach {
                db.forecastDao.insertForecast(
                    ForecastEntity(
                        astronomy = AstronomyEmbedded.fromAstronomy(it.astronomy),
                        date = it.date,
                        city = it.links.city
                    )
                )

                db.hourDao.insert(
                    it.hours.map { hour ->
                        HourEntity(
                            cloud = CloudEmbedded.fromCloud(hour.cloud),
                            hour = hour.hour,
                            humidity = ForecastValueEmbedded.fromForecastValue(hour.humidity),
                            icon = hour.icon,
                            iconPath = hour.iconPath,
                            precipitation = PrecipitationEmbedded.fromPrecipitation(
                                hour.precipitation
                            ),
                            pressure = ForecastValueEmbedded.fromForecastValue(hour.pressure),
                            temperature = ForecastValueEmbedded.fromForecastValue(hour.temperature),
                            wind = WindEmbedded.fromWind(hour.wind),
                            forecastDate = it.date,
                            forecastCity = it.links.city,
                        )
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
