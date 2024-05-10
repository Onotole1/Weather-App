package ru.netology.weatherapp.fragment.forecastdetails

import androidx.recyclerview.widget.DiffUtil

class ForecastDetailsModelDiffUtil : DiffUtil.ItemCallback<ForecastDetailsUiModel>() {
    override fun areItemsTheSame(
        oldItem: ForecastDetailsUiModel,
        newItem: ForecastDetailsUiModel
    ): Boolean =
        when {
            oldItem is ForecastDetailsUiModel.HourUiModel &&
                    newItem is ForecastDetailsUiModel.HourUiModel -> oldItem.hour == newItem.hour

            oldItem is ForecastDetailsUiModel.TextUiModel &&
                    newItem is ForecastDetailsUiModel.TextUiModel -> oldItem.text == newItem.text

            else -> false
        }

    override fun areContentsTheSame(
        oldItem: ForecastDetailsUiModel,
        newItem: ForecastDetailsUiModel
    ): Boolean = oldItem == newItem
}
