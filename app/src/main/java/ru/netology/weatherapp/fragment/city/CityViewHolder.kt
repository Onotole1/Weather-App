package ru.netology.weatherapp.fragment.city

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.checkbox.MaterialCheckBox
import ru.netology.weatherapp.databinding.ItemCityBinding

class CityViewHolder(
    private val binding: ItemCityBinding,
    clickListener: (position: Int) -> Unit,
) : ViewHolder(binding.root) {

    private val onCheckedChangeListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            buttonView.isChecked = !isChecked
            clickListener(adapterPosition)
        }

    fun update(payload: CityUiModel.Payload) {
        payload.selected?.also {
            with(binding.root) {
                updateWithoutListener(it)
            }
        }
    }

    private fun MaterialCheckBox.updateWithoutListener(it: Boolean) {
        setOnCheckedChangeListener(null)
        isChecked = it
        setOnCheckedChangeListener(onCheckedChangeListener)
    }

    fun bind(city: CityUiModel) {
        binding.root.apply {
            text = city.title
            setOnCheckedChangeListener(null)
            isChecked = city.selected
            setOnCheckedChangeListener(onCheckedChangeListener)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            clickListener: (id: Int) -> Unit,
        ): CityViewHolder {
            val binding = ItemCityBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

            return CityViewHolder(binding, clickListener)
        }
    }
}