package ru.netology.weatherapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.netology.weatherapp.databinding.ActivityMainBinding
import ru.netology.weatherapp.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navController = binding.navHostFragment.getFragment<Fragment>().findNavController()

        val viewModel by viewModels<MainViewModel>()

        viewModel.selectedCity.flowWithLifecycle(lifecycle)
            .filter { navController.currentDestination?.id == R.id.forecastFragment }
            .onEach {
                binding.toolbar.title = it?.title
            }
            .launchIn(lifecycleScope)

        binding.toolbar.inflateMenu(R.menu.menu_forecast)

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.select_city) {
                navController.navigate(R.id.action_forecastFragment_to_citiesFragment)
                true
            } else {
                false
            }
        }

        val selectCityItem = binding.toolbar.menu.findItem(R.id.select_city)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.forecastFragment) {
                binding.toolbar.title = viewModel.selectedCity.value?.title
                selectCityItem?.isVisible = true
            } else {
                selectCityItem?.isVisible = false
            }
        }

        binding.toolbar.setupWithNavController(navController)
    }
}
