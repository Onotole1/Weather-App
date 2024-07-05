package ru.netology.weatherapp.fragment.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.netology.weatherapp.ui.theme.ComposeAppTheme
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Composable
fun ForecastItem(model: ForecastUiModel, onForecastClicked: (ForecastUiModel) -> Unit) {
    Card(modifier = Modifier.padding(horizontal = 8.dp), onClick = {
        onForecastClicked(model)
    }) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = model.dateFormatted, fontSize = 34.sp)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf(
                    model.midnightTemperature to model.midnightIcon,
                    model.morningTemperature to model.morningIcon,
                    model.middayTemperature to model.middayIcon,
                    model.eveningTemperature to model.eveningIcon,
                ).forEach { (temperature, icon) ->
                    Column {
                        AsyncImage(
                            model = icon,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp, 16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(text = temperature)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ForecastItemPreview() {
    ComposeAppTheme {
        ForecastItem(
            ForecastUiModel(
                "Санкт-Петербург",
                OffsetDateTime.of(
                    2024,
                    7,
                    4,
                    0,
                    0,
                    0,
                    0,
                    ZoneOffset.ofHours(3),
                ),
                "7 июля",
                midnightTemperature = "+15",
                morningTemperature = "+10",
                middayTemperature = "+11",
                eveningTemperature = "+10"
            )
        ) {}
    }
}