package ru.netology.weatherapp.dao.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.weatherapp.entity.forecast.ForecastEntity
import ru.netology.weatherapp.entity.forecast.HourEntity
import java.time.OffsetDateTime

@Dao
interface HourDao {
    suspend fun getHours(forecastEntity: ForecastEntity): List<HourEntity> = getHours(
        forecastDate = forecastEntity.date,
        forecastCity = forecastEntity.city
    )

    @Query(
        """
        SELECT * FROM hour WHERE city = :forecastCity AND date = :forecastDate
        """
    )
    suspend fun getHours(
        forecastDate: OffsetDateTime,
        forecastCity: String,
    ): List<HourEntity>

    @Query(
        """
        SELECT * FROM hour WHERE city = :forecastCity AND date = :forecastDate AND hour = :hour
        """
    )
    suspend fun getHour(
        forecastDate: OffsetDateTime,
        forecastCity: String,
        hour: Int,
    ): HourEntity

    @Insert(HourEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<HourEntity>)

    @Query("DELETE FROM hour")
    suspend fun clear()
}
