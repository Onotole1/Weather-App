package ru.netology.weatherapp.fragment.forecastdetails

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.weatherapp.R

class ForecastDetailsAdapter :
    ListAdapter<ForecastDetailsUiModel, RecyclerView.ViewHolder>(ForecastDetailsModelDiffUtil()) {
    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ForecastDetailsUiModel.HourUiModel -> R.layout.item_forecast_details_hour
        is ForecastDetailsUiModel.TextUiModel -> R.layout.item_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_forecast_details_hour -> ForecastDetailsItemViewHolder.create(parent)
            R.layout.item_text -> TextItemViewHolder.create(parent)
            else -> error("Unknown view type: $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ForecastDetailsUiModel.HourUiModel -> (holder as ForecastDetailsItemViewHolder)
                .bind(item)

            is ForecastDetailsUiModel.TextUiModel -> (holder as TextItemViewHolder).bind(item)
        }
    }
}
