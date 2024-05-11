package ru.netology.weatherapp.dao.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.weatherapp.entity.city.CityEntity

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    suspend fun getCities(): List<CityEntity>

    @Query("SELECT * FROM city WHERE selected = 1")
    fun getSelectedCity(): Flow<CityEntity?>

    @Query(
        """
        UPDATE city SET
        selected = CASE WHEN id = :id THEN 1 ELSE 0 END
        """
    )
    suspend fun selectCityById(id: Int)

    @Insert(CityEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)
}
