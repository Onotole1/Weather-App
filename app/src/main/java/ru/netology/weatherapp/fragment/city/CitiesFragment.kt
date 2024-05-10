package ru.netology.weatherapp.fragment.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.netology.weatherapp.databinding.FragmentCitiesBinding
import ru.netology.weatherapp.model.City
import ru.netology.weatherapp.model.Status
import ru.netology.weatherapp.viewmodel.cities.CitiesViewModel

@AndroidEntryPoint
class CitiesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCitiesBinding.inflate(inflater, container, false)

        val viewModel by viewModels<CitiesViewModel>()

        binding.search.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.search(newText.orEmpty())

                    return true
                }
            }
        )

        val adapter = CityAdapter(viewModel::setCity)

        binding.list.also {
            it.adapter = adapter
            it.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadCities()
        }

        val chipsListener = ChipGroup.OnCheckedStateChangeListener { _, ids ->
            when {
                binding.moscow.id in ids -> viewModel.setCity(City.MOSCOW.id)
                binding.saintPetersburg.id in ids -> viewModel.setCity(City.SAINT_PETERSBURG.id)
            }
        }

        val chipListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            buttonView.isChecked = !isChecked
        }

        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                adapter.submitList(state.results.map(CityUiModel::fromCity))
                with(binding) {
                    progress.isVisible = state.status is Status.Loading
                    errorGroup.isVisible = state.status is Status.Error
                    retryTitle.text = state.status.error?.message
                    predefinedCities.setOnCheckedStateChangeListener(null)
                    moscow.setOnCheckedChangeListener(null)
                    saintPetersburg.setOnCheckedChangeListener(null)
                    moscow.isChecked = state.cities.any {
                        it.id == City.MOSCOW.id && it.selected
                    }
                    saintPetersburg.isChecked = state.cities.any {
                        it.id == City.SAINT_PETERSBURG.id && it.selected
                    }
                    moscow.setOnCheckedChangeListener(chipListener)
                    saintPetersburg.setOnCheckedChangeListener(chipListener)
                    predefinedCities.setOnCheckedStateChangeListener(chipsListener)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}
