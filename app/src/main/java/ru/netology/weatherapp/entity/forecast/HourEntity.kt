package ru.netology.weatherapp.entity.forecast

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import ru.netology.weatherapp.dto.forecast.Forecast
import ru.netology.weatherapp.dto.forecast.Hour
import java.time.OffsetDateTime

@Entity(
    "hour",
    foreignKeys = [
        ForeignKey(
            entity = ForecastEntity::class,
            parentColumns = ["date", "city"],
            childColumns = ["date", "city"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    primaryKeys = ["hour", "date", "city"],
    indices = [
        Index("date", "city"),
    ]
)
data class HourEntity(
    @Embedded("cloud_")
    val cloud: CloudEmbedded,
    @ColumnInfo("hour")
    val hour: Int,
    @Embedded("humidity_")
    val humidity: ForecastValueEmbedded,
    @ColumnInfo("icon")
    val icon: String,
    @ColumnInfo("icon_path")
    val iconPath: String,
    @Embedded("precipitation_")
    val precipitation: PrecipitationEmbedded,
    @Embedded("pressure_")
    val pressure: ForecastValueEmbedded,
    @Embedded("temperature_")
    val temperature: ForecastValueEmbedded,
    @Embedded("wind_")
    val wind: WindEmbedded,
    @ColumnInfo("date")
    val forecastDate: OffsetDateTime,
    @ColumnInfo("city")
    val forecastCity: String
) {
    companion object {
        fun fromHourAndForecast(hour: Hour, forecast: Forecast) =
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
                forecastDate = forecast.date,
                forecastCity = forecast.links.city,
            )
    }
}
