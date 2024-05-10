package ru.netology.weatherapp.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.netology.weatherapp.repository.city.CityRepository
import ru.netology.weatherapp.repository.city.CityRepositoryImpl
import ru.netology.weatherapp.repository.forecast.ForecastRepository
import ru.netology.weatherapp.repository.forecast.ForecastRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindForecastRepositoryImplToForecastRepository(
        impl: ForecastRepositoryImpl
    ): ForecastRepository

    @Binds
    fun bindCityRepositoryImplToCityRepository(impl: CityRepositoryImpl): CityRepository
}
