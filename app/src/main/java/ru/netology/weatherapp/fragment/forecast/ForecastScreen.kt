package ru.netology.weatherapp.fragment.forecast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import ru.netology.weatherapp.fragment.ErrorScreen
import ru.netology.weatherapp.fragment.LoadingScreen
import ru.netology.weatherapp.model.Status
import ru.netology.weatherapp.viewmodel.forecast.ForecastState
import ru.netology.weatherapp.viewmodel.forecast.ForecastViewModel
import java.time.OffsetDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    viewModel: ForecastViewModel,
    mapper: ForecastUiModelMapper,
    onForecastClicked: (ForecastUiModel) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    when {
        state.isEmptyLoading -> {
            LoadingScreen()
        }

        state.isEmptyError -> {
            ErrorScreen(
                error = requireNotNull(state.status.error),
                onRetryClicked = viewModel::loadForecast,
            )
        }

        else -> {
            val pullRefreshState = rememberPullToRefreshState()

            Box(Modifier.nestedScroll(pullRefreshState.nestedScrollConnection)) {
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(mapper.fromList(state.forecast), key = { it.dateFormatted }) {
                        ForecastItem(it, onForecastClicked)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullRefreshState,
                )
            }

            state.status.error?.localizedMessage?.let {
                SideEffect { viewModel.handleError() }
                Snackbar { Text(it) }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ForecastScreenSuccessPreview() {
    ForecastScreen(SuccessForecastViewModel(), ForecastUiModelMapper(LocalContext.current))
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ForecastErrorPreview() {
    ForecastScreen(ErrorForecastViewModel(), ForecastUiModelMapper(LocalContext.current))
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ForecastLoadingPreview() {
    ForecastScreen(LoadingForecastViewModel(), ForecastUiModelMapper(LocalContext.current))
}

private class ErrorForecastViewModel : ForecastViewModel {
    override val state: StateFlow<ForecastState> = MutableStateFlow(
        ForecastState(
            status = Status.Error(RuntimeException("Test error"))
        )
    )

    override fun loadForecast() = Unit

    override fun handleError() = Unit
}

private class LoadingForecastViewModel : ForecastViewModel {
    override val state: StateFlow<ForecastState> = MutableStateFlow(
        ForecastState(status = Status.Loading)
    )

    override fun loadForecast() = Unit

    override fun handleError() = Unit
}

private class SuccessForecastViewModel : ForecastViewModel {
    override val state: StateFlow<ForecastState> = MutableStateFlow(
        ForecastState(
            forecast = List(10) { itemIndex ->
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
                        itemIndex + 1,
                        0,
                        0,
                        0,
                        0,
                        ZoneOffset.ofHours(3),
                    ),
                    links = Links("saint_petersburg"),
                    hours = List(4) {
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
                    })
            }
        )
    )

    override fun loadForecast() = Unit

    override fun handleError() = Unit
}