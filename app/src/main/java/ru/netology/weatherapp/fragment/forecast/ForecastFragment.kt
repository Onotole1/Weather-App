package ru.netology.weatherapp.fragment.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.netology.weatherapp.R
import ru.netology.weatherapp.databinding.FragmentForecastBinding
import ru.netology.weatherapp.extensions.addItemDecoration
import ru.netology.weatherapp.fragment.forecastdetails.ForecastDetailsFragment
import ru.netology.weatherapp.viewmodel.forecast.ForecastViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    @Inject
    lateinit var mapper: ForecastUiModelMapper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = ForecastAdapter {
            findNavController().navigate(
                R.id.action_forecastFragment_to_forecastDetailsFragment,
                ForecastDetailsFragment.createArgs(it.date, it.dateFormatted)
            )
        }

        val binding = FragmentForecastBinding.inflate(inflater, container, false)

        binding.list.adapter = adapter

        val listOffset = resources.getDimensionPixelSize(R.dimen.small_offset)
        binding.list.addItemDecoration { outRect, _, _, _ ->
            outRect.bottom += listOffset
            outRect.left += listOffset
            outRect.right += listOffset
        }

        val viewModel by viewModels<ForecastViewModel>()

        binding.refresh.setOnRefreshListener {
            viewModel.loadForecast()
        }

        val retryListener = View.OnClickListener { viewModel.loadForecast() }

        binding.retryButton.setOnClickListener(retryListener)

        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                adapter.submitList(mapper.fromList(state.forecast))
                binding.progress.isVisible = state.isEmptyLoading
                binding.refresh.isRefreshing = state.isRefreshing
                binding.errorGroup.isVisible = state.isEmptyError
                binding.retryTitle.text = state.status.error?.message
                if (state.notEmptyError) {
                    Snackbar.make(
                        binding.root,
                        state.status.error?.message.orEmpty(),
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction(R.string.retry, retryListener)
                        .show()
                    viewModel.handleError()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}