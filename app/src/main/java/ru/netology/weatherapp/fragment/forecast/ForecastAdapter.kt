package ru.netology.weatherapp.fragment.forecast

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ForecastAdapter(
    private val onForecastClicked: (ForecastUiModel) -> Unit,
) : ListAdapter<ForecastUiModel, ForecastViewHolder>(ForecastUiModelDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder =
        ForecastViewHolder.create(parent) {
            onForecastClicked(getItem(it))
        }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
