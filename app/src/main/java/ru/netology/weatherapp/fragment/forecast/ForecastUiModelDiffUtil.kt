package ru.netology.weatherapp.fragment.forecast

import androidx.recyclerview.widget.DiffUtil

class ForecastUiModelDiffUtil : DiffUtil.ItemCallback<ForecastUiModel>() {
    override fun areItemsTheSame(oldItem: ForecastUiModel, newItem: ForecastUiModel): Boolean =
        oldItem.city == newItem.city && oldItem.date == newItem.date

    override fun areContentsTheSame(
        oldItem: ForecastUiModel,
        newItem: ForecastUiModel
    ): Boolean = oldItem == newItem
}
