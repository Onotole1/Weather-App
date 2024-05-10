package ru.netology.weatherapp.entity.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.mcarle.konvert.api.KonvertFrom
import io.mcarle.konvert.api.KonvertTo
import ru.netology.weatherapp.dto.city.CityDto
import ru.netology.weatherapp.model.City

@Entity("city")
@KonvertTo(City::class)
data class CityEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("title_dative")
    val titleDative: String?,
    @ColumnInfo("title_prepositional")
    val titlePrepositional: String?,
    @ColumnInfo("selected")
    val selected: Boolean = false,
) {
    @KonvertFrom(CityDto::class)
    companion object
}
