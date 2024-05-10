package ru.netology.weatherapp.entity.forecast


import androidx.room.ColumnInfo
import io.mcarle.konvert.api.KonvertFrom
import io.mcarle.konvert.api.KonvertTo
import ru.netology.weatherapp.dto.forecast.Astronomy

@KonvertTo(Astronomy::class)
data class AstronomyEmbedded(
    @ColumnInfo("length_day_human")
    val lengthDayHuman: String,
    @ColumnInfo("moon_illuminated")
    val moonIlluminated: Int,
    @ColumnInfo("moon_phase")
    val moonPhase: String,
    @ColumnInfo("sunrise")
    val sunrise: String,
    @ColumnInfo("sunset")
    val sunset: String
) {
    @KonvertFrom(Astronomy::class)
    companion object
}