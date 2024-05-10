package ru.netology.weatherapp.entity.forecast


import androidx.room.ColumnInfo
import io.mcarle.konvert.api.KonvertFrom
import io.mcarle.konvert.api.KonvertTo
import ru.netology.weatherapp.dto.forecast.Precipitation

@KonvertTo(Precipitation::class)
data class PrecipitationEmbedded(
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("value")
    val value: String
) {
    @KonvertFrom(Precipitation::class)
    companion object
}