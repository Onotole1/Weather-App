package ru.netology.weatherapp.fragment.forecastdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import ru.netology.weatherapp.ui.theme.ComposeAppTheme
import ru.netology.weatherapp.viewmodel.forecastdetails.ForecastDetailsViewModelFactory
import ru.netology.weatherapp.viewmodel.forecastdetails.ForecastDetailsViewModelImpl
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
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            ComposeAppTheme {
                val viewModel by viewModels<ForecastDetailsViewModelImpl>(
                    extrasProducer = {
                        defaultViewModelCreationExtras
                            .withCreationCallback<ForecastDetailsViewModelFactory> { factory ->
                                @Suppress("DEPRECATION")
                                factory.create(
                                    requireArguments()
                                        .getSerializable(DATE_EXTRA) as OffsetDateTime
                                )
                            }
                    }
                )

                val context = LocalContext.current
                ForecastDetailsScreen(
                    viewModel = viewModel,
                    remember { ForecastDetailsUiModelMapper(context) },
                )
            }
        }
    }
}
