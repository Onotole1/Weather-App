package ru.netology.weatherapp.entity.forecast


import androidx.room.Embedded
import io.mcarle.konvert.api.KonvertFrom
import io.mcarle.konvert.api.KonvertTo
import ru.netology.weatherapp.dto.forecast.ForecastValue
import ru.netology.weatherapp.dto.forecast.Wind

@KonvertTo(Wind::class)
data class WindEmbedded(
    @Embedded("direction_")
    val direction: DirectionEmbedded,
    @Embedded("speed_")
    val speed: ForecastValue
) {
    @KonvertFrom(Wind::class)
    companion object
}