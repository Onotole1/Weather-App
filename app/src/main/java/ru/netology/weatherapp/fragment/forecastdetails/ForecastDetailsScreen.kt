package ru.netology.weatherapp.fragment.forecastdetails

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.netology.weatherapp.dto.forecast.Astronomy
import ru.netology.weatherapp.dto.forecast.Cloud
import ru.netology.weatherapp.dto.forecast.Direction
import ru.netology.weatherapp.dto.forecast.Forecast
import ru.netology.weatherapp.dto.forecast.ForecastValue
import ru.netology.weatherapp.dto.forecast.Hour
import ru.netology.weatherapp.dto.forecast.Links
import ru.netology.weatherapp.dto.forecast.Precipitation
import ru.netology.weatherapp.dto.forecast.Wind
import ru.netology.weatherapp.ui.theme.ComposeAppTheme
import ru.netology.weatherapp.viewmodel.forecastdetails.ForecastDetailsViewModel
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Composable
fun ForecastDetailsScreen(
    viewModel: ForecastDetailsViewModel,
    forecastDetailsUiModelMapper: ForecastDetailsUiModelMapper,
) {
    val forecast by viewModel.forecast.collectAsState()

    LazyColumn {
        forecast?.let { forecast ->
            items(forecastDetailsUiModelMapper.map(forecast)) { item ->
                when (item) {
                    is ForecastDetailsUiModel.HourUiModel -> ForecastDetailsItem(item)
                    is ForecastDetailsUiModel.TextUiModel -> TextItem(item)
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ForecastDetailsScreenPreview() {
    ComposeAppTheme {
        ForecastDetailsScreen(
            viewModel = PreviewForecastDetailsViewModel(),
            ForecastDetailsUiModelMapper(LocalContext.current),
        )
    }
}

private class PreviewForecastDetailsViewModel : ForecastDetailsViewModel {
    override val forecast: StateFlow<Forecast?> = MutableStateFlow(
        Forecast(
            Astronomy(
                lengthDayHuman = "18 ч 34 мин",
                moonIlluminated = 9,
                moonPhase = "waning",
                sunrise = "03:46",
                sunset = "22:20",
            ),
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
            links = Links("saint_petersburg"),
            hours = listOf(
                Hour(
                    Cloud(
                        title = "пасмурно",
                        value = "cloudy",
                    ),
                    hour = 0,
                    humidity = ForecastValue(
                        avg = 94,
                        min = 94,
                        max = 94,
                    ),
                    icon = "cloudy_none_night",
                    iconPath = "//pogoda.ngs.ru/static/img/ico/small/cloudy_none_night.png",
                    precipitation = Precipitation(
                        title = "без осадков",
                        value = "none",
                    ),
                    pressure = ForecastValue(
                        avg = 755,
                        min = 755,
                        max = 755,
                    ),
                    temperature = ForecastValue(
                        avg = 14,
                        min = 13,
                        max = 15,
                    ),
                    wind = Wind(
                        direction = Direction(
                            title = "южный",
                            titleLetter = "Ю",
                            titleShort = "южн",
                            value = "south",
                        ),
                        ForecastValue(
                            avg = 2,
                            min = 2,
                            max = 2,
                        )
                    ),
                )
            )
        )
    )
}
