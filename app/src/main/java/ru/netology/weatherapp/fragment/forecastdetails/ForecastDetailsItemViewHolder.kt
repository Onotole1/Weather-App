package ru.netology.weatherapp.fragment.forecastdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.weatherapp.databinding.ItemForecastDetailsHourBinding
import ru.netology.weatherapp.extensions.load

class ForecastDetailsItemViewHolder(
    private val binding: ItemForecastDetailsHourBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ForecastDetailsUiModel.HourUiModel) {
        with(binding) {
            date.text = model.hour
            icon.load(model.icon)
            temperature.text = model.temperature
            humidity.text = model.humidity
            precipitation.text = model.precipitation
            pressure.text = model.pressure
            wind.text = model.wind
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
        ): ForecastDetailsItemViewHolder = ForecastDetailsItemViewHolder(
            ItemForecastDetailsHourBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}