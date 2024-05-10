package ru.netology.weatherapp.fragment.city

import androidx.recyclerview.widget.DiffUtil

class CityUiModelDiffUtil : DiffUtil.ItemCallback<CityUiModel>() {
    override fun areItemsTheSame(oldItem: CityUiModel, newItem: CityUiModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CityUiModel,
        newItem: CityUiModel
    ): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: CityUiModel, newItem: CityUiModel): Any =
        CityUiModel.Payload(selected = newItem.selected.takeIf { it != oldItem.selected })
}
