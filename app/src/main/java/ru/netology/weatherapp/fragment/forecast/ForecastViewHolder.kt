package ru.netology.weatherapp.fragment.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.weatherapp.databinding.ItemForecastBinding
import ru.netology.weatherapp.extensions.load

class ForecastViewHolder(
    private val binding: ItemForecastBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ForecastUiModel) {
        with(binding) {
            date.text = model.dateFormatted
            midnight.text = model.midnightTemperature
            midnightIcon.load(model.midnightIcon)
            morning.text = model.morningTemperature
            morningIcon.load(model.morningIcon)
            midday.text = model.middayTemperature
            middayIcon.load(model.middayIcon)
            evening.text = model.eveningTemperature
            eveningIcon.load(model.eveningIcon)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onForecastClicked: (position: Int) -> Unit,
        ): ForecastViewHolder {
            val binding = ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

            return ForecastViewHolder(binding).also { holder ->
                binding.root.setOnClickListener {
                    onForecastClicked(holder.adapterPosition)
                }
            }
        }
    }
}
