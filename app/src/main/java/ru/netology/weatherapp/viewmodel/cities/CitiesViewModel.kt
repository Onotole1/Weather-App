package ru.netology.weatherapp.viewmodel.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.netology.weatherapp.model.Status
import ru.netology.weatherapp.repository.city.CityRepository
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val cityRepository: CityRepository,
) : ViewModel() {
    private var loadCitiesJob: Job? = null
    private val _state = MutableStateFlow(CitiesState())
    val state = _state.asStateFlow()

    init {
        loadCities()
    }

    fun search(query: String) {
        _state.update {
            it.copy(query = query)
        }
    }

    fun loadCities() {
        loadCitiesJob?.cancel()
        loadCitiesJob = viewModelScope.launch {
            _state.update { it.copy(status = Status.Loading) }

            runCatching {
                cityRepository.getCities()
            }
                .onSuccess { cities ->
                    _state.update {
                        it.copy(cities = cities, status = Status.Idle)
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(status = Status.Error(error))
                    }
                }
        }
    }

    fun setCity(id: Int) {
        viewModelScope.launch {
            cityRepository.selectCity(id)
            loadCities()
        }
    }
}