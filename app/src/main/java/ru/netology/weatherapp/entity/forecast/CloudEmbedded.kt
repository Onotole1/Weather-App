package ru.netology.weatherapp.entity.forecast


import androidx.room.ColumnInfo
import io.mcarle.konvert.api.KonvertFrom
import io.mcarle.konvert.api.KonvertTo
import ru.netology.weatherapp.dto.forecast.Cloud

@KonvertTo(Cloud::class)
data class CloudEmbedded(
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("value")
    val value: String
) {
    @KonvertFrom(Cloud::class)
    companion object
}
