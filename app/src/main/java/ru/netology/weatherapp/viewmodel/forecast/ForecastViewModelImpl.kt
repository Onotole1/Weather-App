package ru.netology.weatherapp.viewmodel.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.netology.weatherapp.model.City
import ru.netology.weatherapp.model.Status
import ru.netology.weatherapp.repository.city.CityRepository
import ru.netology.weatherapp.repository.forecast.ForecastRepository
import javax.inject.Inject

@HiltViewModel
class ForecastViewModelImpl @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val cityRepository: CityRepository,
) : ViewModel(), ForecastViewModel {

    private var forecastJob: Job? = null
    private val _state = MutableStateFlow(ForecastState())
    override val state = _state.asStateFlow()

    init {
        loadForecast()
    }

    override fun loadForecast() {
        _state.update { it.copy(status = Status.Loading) }

        forecastJob?.cancel()
        forecastJob = viewModelScope.launch {
            cityRepository.getSelectedCity()
                .collectLatest(::loadForecast)
        }
    }

    private suspend fun loadForecast(city: City) {
        runCatching {
            // Скачиваем закешированный прогноз в первую очередь
            _state.update {
                it.copy(
                    forecast = forecastRepository.getForecast(
                        city = city.name,
                        isFresh = false,
                    ),
                )
            }

            // Затем запрашиваем свежий прогноз
            _state.update {
                it.copy(
                    forecast = forecastRepository.getForecast(
                        city = city.name,
                        isFresh = true,
                    ),
                    status = Status.Idle,
                )
            }
        }
            .onFailure { error ->
                _state.update { state ->
                    state.copy(status = Status.Error(error))
                }
            }
    }

    override fun handleError() {
        _state.update {
            it.copy(
                status = if (it.status is Status.Error) {
                    Status.Idle
                } else {
                    it.status
                }
            )
        }
    }
}
