package ru.netology.weatherapp.fragment.forecastdetails

sealed interface ForecastDetailsUiModel {

    data class TextUiModel(val text: String) : ForecastDetailsUiModel

    data class HourUiModel(
        val hour: String,
        val humidity: String,
        val icon: String,
        val precipitation: String,
        val pressure: String,
        val temperature: String,
        val wind: String,
    ) : ForecastDetailsUiModel
}
