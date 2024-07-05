package ru.netology.weatherapp.fragment.city

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.netology.weatherapp.ui.theme.ComposeAppTheme

/**
 * Элемент списка выбора города. Работает по принципу выбора одного города т.е. снять чекбокс нельзя
 */
@Composable
fun CityItem(city: CityUiModel, onCitySelected: (CityUiModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .clickable(role = Role.Checkbox, onClick = {
                onCitySelected(city)
            }),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(modifier = Modifier.weight(1F), text = city.title)
        Checkbox(checked = city.selected, onCheckedChange = {
            onCitySelected(city)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun CityItemPreview() {
    ComposeAppTheme {
        var city by remember { mutableStateOf(CityUiModel(id = 100, "Новосибирск")) }
        CityItem(city = city) {
            city = city.copy(selected = it.id == city.id)
        }
    }
}
