package ru.netology.weatherapp.fragment.city

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.netology.weatherapp.fragment.ErrorScreen
import ru.netology.weatherapp.fragment.LoadingScreen
import ru.netology.weatherapp.model.City
import ru.netology.weatherapp.model.Status
import ru.netology.weatherapp.ui.theme.ComposeAppTheme
import ru.netology.weatherapp.viewmodel.cities.CitiesState
import ru.netology.weatherapp.viewmodel.cities.CitiesViewModel

@Composable
fun CitiesScreen(viewModel: CitiesViewModel) {
    val state by viewModel.state.collectAsState()

    when (val status = state.status) {
        Status.Loading -> LoadingScreen()
        is Status.Error -> ErrorScreen(error = status.value, onRetryClicked = viewModel::loadCities)
        Status.Idle -> {
            Column {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.query,
                    onValueChange = viewModel::search
                )

                val selectedCity = state.cities.find { it.selected }
                CityChipRow(
                    selectedCityId = selectedCity?.id,
                    cities = listOf(City.MOSCOW, City.SAINT_PETERSBURG),
                    onCitySelected = viewModel::setCity,
                )

                LazyColumn {
                    items(state.results.map(CityUiModel::fromCity), key = { it.id }) {
                        CityItem(city = it) { selectedCity ->
                            viewModel.setCity(selectedCity.id)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CitiesScreenSuccessPreview() {
    ComposeAppTheme {
        CitiesScreen(SuccessCityViewModel())
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CitiesScreenErrorPreview() {
    ComposeAppTheme {
        CitiesScreen(ErrorCityViewModel())
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CitiesScreenLoadingPreview() {
    ComposeAppTheme {
        CitiesScreen(LoadingCityViewModel())
    }
}

private class ErrorCityViewModel : CitiesViewModel {
    override val state: StateFlow<CitiesState> =
        MutableStateFlow(CitiesState(status = Status.Error(RuntimeException("Кончился интернет"))))
}

private class LoadingCityViewModel : CitiesViewModel {
    override val state: StateFlow<CitiesState> =
        MutableStateFlow(CitiesState(status = Status.Loading))
}

private class SuccessCityViewModel : CitiesViewModel {
    override val state = MutableStateFlow(
        CitiesState(
            cities = listOf(
                City.MOSCOW,
                City.SAINT_PETERSBURG,
                City(100, "novosibirsk", title = "Новосибирск")
            )
        )
    )

    override fun search(query: String) {
        state.update { state ->
            state.copy(
                query = query,
                cities = state.cities.filter { it.title.contains(query, true) }
            )
        }
    }

    override fun setCity(id: Int) {
        state.update { state ->
            state.copy(cities = state.cities.map { it.copy(selected = it.id == id) })
        }
    }
}