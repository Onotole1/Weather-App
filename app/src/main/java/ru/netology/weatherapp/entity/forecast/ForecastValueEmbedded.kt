package ru.netology.weatherapp.entity.forecast

import androidx.room.ColumnInfo
import io.mcarle.konvert.api.KonvertFrom
import io.mcarle.konvert.api.KonvertTo
import ru.netology.weatherapp.dto.forecast.ForecastValue

@KonvertTo(ForecastValue::class)
data class ForecastValueEmbedded(
    @ColumnInfo("avg")
    val avg: Int,
    @ColumnInfo("max")
    val max: Int,
    @ColumnInfo("min")
    val min: Int
) {
    @KonvertFrom(ForecastValue::class)
    companion object
}
