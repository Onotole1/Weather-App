package ru.netology.weatherapp.viewmodel.forecastdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ru.netology.weatherapp.repository.city.CityRepository
import ru.netology.weatherapp.repository.forecast.ForecastRepository
import java.time.OffsetDateTime

@HiltViewModel(assistedFactory = ForecastDetailsViewModelFactory::class)
class ForecastDetailsViewModel @AssistedInject constructor(
    @Assisted private val date: OffsetDateTime,
    private val cityRepository: CityRepository,
    private val forecastRepository: ForecastRepository,
) : ViewModel() {

    val forecast = flow {
        val selectedCity = cityRepository.getSelectedCity().first()

        emit(forecastRepository.getForecast(selectedCity.name, date))
    }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
