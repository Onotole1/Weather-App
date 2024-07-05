package ru.netology.weatherapp.fragment.city

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.netology.weatherapp.model.City
import ru.netology.weatherapp.ui.theme.ComposeAppTheme

@Composable
fun CityChip(selectedCityId: Int?, city: City, onCitySelected: (Int) -> Unit) {
    InputChip(
        selected = selectedCityId == city.id,
        onClick = {
            onCitySelected(city.id)
        },
        label = {
            Text(text = city.title)
        },
    )
}

@Composable
fun CityChipRow(selectedCityId: Int?, cities: List<City>, onCitySelected: (Int) -> Unit) {
    Row(Modifier.padding(start = 16.dp)) {
        cities.forEach {
            CityChip(selectedCityId = selectedCityId, city = it, onCitySelected)
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun CityChipRowPreview() {
    ComposeAppTheme {
        var selectedCity by remember { mutableIntStateOf(City.MOSCOW.id) }
        CityChipRow(
            selectedCityId = selectedCity,
            cities = listOf(City.MOSCOW, City.SAINT_PETERSBURG)
        ) {
            selectedCity = it
        }
    }
}
