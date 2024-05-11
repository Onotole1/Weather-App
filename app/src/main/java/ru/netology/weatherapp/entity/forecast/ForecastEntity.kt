package ru.netology.weatherapp.entity.forecast

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import ru.netology.weatherapp.dto.forecast.Forecast
import java.time.OffsetDateTime

@Entity("forecast", primaryKeys = ["date", "city"])
data class ForecastEntity(
    @Embedded("astronomy_")
    val astronomy: AstronomyEmbedded,
    @ColumnInfo("date", index = true)
    val date: OffsetDateTime,
    @ColumnInfo("city", index = true)
    val city: String
) {
    companion object {
        fun fromForecast(forecast: Forecast): ForecastEntity =
            with(forecast) {
                ForecastEntity(
                    astronomy = AstronomyEmbedded.fromAstronomy(astronomy),
                    date = date,
                    city = links.city
                )
            }
    }
}
