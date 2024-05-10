package ru.netology.weatherapp.entity.forecast


import androidx.room.ColumnInfo
import io.mcarle.konvert.api.KonvertFrom
import io.mcarle.konvert.api.KonvertTo
import ru.netology.weatherapp.dto.forecast.Direction

@KonvertTo(Direction::class)
data class DirectionEmbedded(
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("title_letter")
    val titleLetter: String,
    @ColumnInfo("title_short")
    val titleShort: String,
    @ColumnInfo("value")
    val value: String
) {
    @KonvertFrom(Direction::class)
    companion object
}
