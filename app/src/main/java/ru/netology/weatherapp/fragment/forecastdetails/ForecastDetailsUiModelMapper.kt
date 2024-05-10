package ru.netology.weatherapp.fragment.forecastdetails

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import ru.netology.weatherapp.R
import ru.netology.weatherapp.dto.forecast.Forecast
import javax.inject.Inject

class ForecastDetailsUiModelMapper @Inject constructor(
    @ActivityContext private val context: Context
) {
    fun map(dto: Forecast): List<ForecastDetailsUiModel> = listOf(
        ForecastDetailsUiModel.TextUiModel(
            context.getString(R.string.sunrise, dto.astronomy.sunrise)
        ),
        ForecastDetailsUiModel.TextUiModel(
            context.getString(R.string.sunset, dto.astronomy.sunset)
        ),
        ForecastDetailsUiModel.TextUiModel(
            context.getString(R.string.length_day_human, dto.astronomy.lengthDayHuman)
        ),
    ) + dto.hours.map {
        ForecastDetailsUiModel.HourUiModel(
            hour = context.resources.getQuantityString(R.plurals.hours, it.hour, it.hour),
            humidity = context.getString(R.string.humidity, it.humidity.avg),
            icon = "http:${it.iconPath}",
            precipitation = it.precipitation.title.replaceFirstChar(Char::uppercase),
            pressure = context.getString(R.string.pressure, it.pressure.avg),
            temperature = it.temperature.avg.toString(),
            wind = context.getString(R.string.wind, it.wind.direction.title, it.wind.speed.avg),
        )
    }
}
