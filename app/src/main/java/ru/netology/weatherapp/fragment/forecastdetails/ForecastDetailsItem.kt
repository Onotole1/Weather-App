package ru.netology.weatherapp.fragment.forecastdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.netology.weatherapp.ui.theme.ComposeAppTheme

@Composable
fun ForecastDetailsItem(model: ForecastDetailsUiModel.HourUiModel, modifier: Modifier = Modifier) {
    Card(
        modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(Modifier.fillMaxWidth()) {
                ProvideTextStyle(TextStyle(fontSize = 34.sp)) {
                    Text(model.hour)
                    Spacer(Modifier.weight(1F))
                    AsyncImage(
                        modifier = Modifier
                            .size(width = 20.dp, height = 16.dp)
                            .align(Alignment.CenterVertically),
                        model = model.icon,
                        contentDescription = null,
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(model.temperature)
                }
            }

            val verticalOffset = 4.dp
            Spacer(Modifier.height(verticalOffset))
            Text(model.humidity)
            Spacer(Modifier.height(verticalOffset))
            Text(model.precipitation)
            Spacer(Modifier.height(verticalOffset))
            Text(model.pressure)
            Spacer(Modifier.height(verticalOffset))
            Text(model.wind)
        }
    }
}

@Composable
@Preview
fun ForecastDetailsItemPreview() {
    ComposeAppTheme {
        ForecastDetailsItem(
            ForecastDetailsUiModel.HourUiModel(
                hour = "6 часов",
                precipitation = "Влажность: 91%",
                humidity = "Влажность: 91%",
                pressure = "Давление: 727 мм рт. ст.",
                wind = "Ветер южный 5 м/с",
                temperature = "+10°C",
                icon = "https://pogoda.ngs.ru/static/img/ico/small/cloudy_none_night.png"
            ),
        )
    }
}