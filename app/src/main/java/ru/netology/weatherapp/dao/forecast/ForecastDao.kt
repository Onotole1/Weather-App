package ru.netology.weatherapp.dao.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.weatherapp.entity.forecast.ForecastEntity
import java.time.OffsetDateTime

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast WHERE city = :city")
    suspend fun getForecasts(city: String): List<ForecastEntity>

    @Query("SELECT * FROM forecast WHERE city = :city AND date = :date")
    suspend fun getForecast(city: String, date: OffsetDateTime): ForecastEntity

    @Insert(ForecastEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecasts: ForecastEntity)

    @Query("DELETE FROM forecast")
    suspend fun clear()

    @Query("SELECT COUNT(*) == 0 FROM forecast WHERE city = :city")
    suspend fun isEmpty(city: String): Boolean
}
