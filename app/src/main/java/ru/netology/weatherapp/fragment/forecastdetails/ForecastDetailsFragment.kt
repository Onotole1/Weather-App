package ru.netology.weatherapp.fragment.forecastdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.netology.weatherapp.R
import ru.netology.weatherapp.databinding.FragmentForecastDetailsBinding
import ru.netology.weatherapp.extensions.addItemDecoration
import ru.netology.weatherapp.viewmodel.forecastdetails.ForecastDetailsViewModel
import ru.netology.weatherapp.viewmodel.forecastdetails.ForecastDetailsViewModelFactory
import java.time.OffsetDateTime
import javax.inject.Inject

@AndroidEntryPoint
class ForecastDetailsFragment : Fragment() {

    companion object {
        private const val DATE_EXTRA = "DATE_EXTRA"

        fun createArgs(dateTime: OffsetDateTime, dateFormatted: String): Bundle = bundleOf(
            DATE_EXTRA to dateTime,
            "title" to dateFormatted,
        )
    }

    @Inject
    lateinit var mapper: ForecastDetailsUiModelMapper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)

        val viewModel by viewModels<ForecastDetailsViewModel>(
            extrasProducer = {
                defaultViewModelCreationExtras.withCreationCallback<ForecastDetailsViewModelFactory> { factory ->
                    @Suppress("DEPRECATION")
                    factory.create(requireArguments().getSerializable(DATE_EXTRA) as OffsetDateTime)
                }
            }
        )

        val adapter = ForecastDetailsAdapter()

        binding.root.adapter = adapter

        val listOffset = resources.getDimensionPixelSize(R.dimen.small_offset)
        binding.root.addItemDecoration { outRect, view, _, _ ->
            if (view.id == R.id.item_forecast_details_hour) {
                outRect.bottom += listOffset
                outRect.left += listOffset
                outRect.right += listOffset
            }
        }

        viewModel.forecast.map { it?.let(mapper::map) }
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(adapter::submitList)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}
