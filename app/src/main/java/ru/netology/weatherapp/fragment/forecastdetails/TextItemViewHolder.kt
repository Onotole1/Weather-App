package ru.netology.weatherapp.fragment.forecastdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.weatherapp.databinding.ItemTextBinding

class TextItemViewHolder(
    private val binding: ItemTextBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ForecastDetailsUiModel.TextUiModel) {
        binding.root.text = model.text
    }

    companion object {
        fun create(
            parent: ViewGroup,
        ): TextItemViewHolder = TextItemViewHolder(
            ItemTextBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}
