package ru.netology.weatherapp.fragment.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.weatherapp.R
import ru.netology.weatherapp.fragment.forecastdetails.ForecastDetailsFragment
import ru.netology.weatherapp.viewmodel.forecast.ForecastViewModelImpl

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        val viewModel by viewModels<ForecastViewModelImpl>()

        setContent {
            ForecastScreen(viewModel, ForecastUiModelMapper(requireContext())) {
                findNavController().navigate(
                    R.id.action_forecastFragment_to_forecastDetailsFragment,
                    ForecastDetailsFragment.createArgs(it.date, it.dateFormatted)
                )
            }
        }
    }
}