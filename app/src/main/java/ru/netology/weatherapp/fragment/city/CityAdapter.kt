package ru.netology.weatherapp.fragment.city

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class CityAdapter(
    private val clickListener: (id: Int) -> Unit,
) : ListAdapter<CityUiModel, CityViewHolder>(CityUiModelDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder.create(parent) {
            clickListener(getItem(it).id)
        }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CityViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach {
                if (it is CityUiModel.Payload) {
                    holder.update(it)
                }
            }
        }
    }
}
