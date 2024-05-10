package ru.netology.weatherapp.db.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.netology.weatherapp.dao.city.CityDao
import ru.netology.weatherapp.db.WeatherDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WeatherDbModule {

    @Provides
    @Singleton
    fun provideWeatherDb(@ApplicationContext context: Context): WeatherDb = Room.databaseBuilder(
        context,
        WeatherDb::class.java,
        "weather_db"
    )
        .build()

    @Provides
    fun provideCityDao(db: WeatherDb): CityDao = db.cityDao
}
