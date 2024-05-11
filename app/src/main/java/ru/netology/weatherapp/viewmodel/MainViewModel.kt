package ru.netology.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.netology.weatherapp.repository.city.CityRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: CityRepository,
) : ViewModel() {
    val selectedCity = repository.getSelectedCity()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
