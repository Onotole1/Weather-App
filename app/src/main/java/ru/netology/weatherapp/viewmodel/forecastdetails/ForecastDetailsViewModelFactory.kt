package ru.netology.weatherapp.viewmodel.forecastdetails

import dagger.assisted.AssistedFactory
import java.time.OffsetDateTime

@AssistedFactory
interface ForecastDetailsViewModelFactory {
    fun create(date: OffsetDateTime): ForecastDetailsViewModelImpl
}
