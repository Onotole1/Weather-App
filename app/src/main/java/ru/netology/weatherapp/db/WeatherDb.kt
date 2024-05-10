package ru.netology.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.netology.weatherapp.dao.city.CityDao
import ru.netology.weatherapp.dao.forecast.ForecastDao
import ru.netology.weatherapp.dao.forecast.HourDao
import ru.netology.weatherapp.entity.city.CityEntity
import ru.netology.weatherapp.entity.converters.OffsetDateTimeRoomConverter
import ru.netology.weatherapp.entity.forecast.ForecastEntity
import ru.netology.weatherapp.entity.forecast.HourEntity

@Database(
    entities = [HourEntity::class, ForecastEntity::class, CityEntity::class],
    version = 1,
)
@TypeConverters(OffsetDateTimeRoomConverter::class)
abstract class WeatherDb : RoomDatabase() {
    abstract val forecastDao: ForecastDao
    abstract val hourDao: HourDao
    abstract val cityDao: CityDao
}
