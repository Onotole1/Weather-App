package ru.netology.weatherapp.fragment.forecast

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import ru.netology.weatherapp.R
import ru.netology.weatherapp.dto.forecast.Forecast
import ru.netology.weatherapp.dto.forecast.ForecastValue
import ru.netology.weatherapp.dto.forecast.Hour
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ForecastUiModelMapper @Inject constructor(@ActivityContext private val context: Context) {
    private val formatter = DateTimeFormatter.ofPattern("d MMM")

    private fun fromDto(dto: Forecast): ForecastUiModel = ForecastUiModel(
        date = dto.date,
        city = dto.links.city,
        dateFormatted = formatter.format(dto.date),
        midnightTemperature = formatTemperature(context, dto.hours[0].temperature),
        midnightIcon = createImageUrl(dto.hours[0]),
        morningTemperature = formatTemperature(context, dto.hours[1].temperature),
        morningIcon = createImageUrl(dto.hours[1]),
        middayTemperature = formatTemperature(context, dto.hours[2].temperature),
        middayIcon = createImageUrl(dto.hours[2]),
        eveningTemperature = formatTemperature(context, dto.hours[3].temperature),
        eveningIcon = createImageUrl(dto.hours[3]),
    )

    fun fromList(list: List<Forecast>): List<ForecastUiModel> = list.map(::fromDto)

    private fun createImageUrl(hour: Hour): String = "http:${hour.iconPath}"

    private fun formatTemperature(context: Context, temperature: ForecastValue): String =
        context.getString(R.string.temperature_celsius, temperature.avg)
}
