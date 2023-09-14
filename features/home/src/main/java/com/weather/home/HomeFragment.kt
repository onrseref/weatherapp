package com.weather.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.weather.base.types.FragmentBindingInflater
import com.weather.base.ui.BaseFragment
import com.weather.data.dto.WeatherResponse
import com.weather.home.adapter.HomeAdapter
import com.weather.home.databinding.FragmentHomeBinding
import com.weather.home.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var homeAdapter: HomeAdapter? = null

    private val viewModel: HomeViewModel by viewModels()
    override fun inflate(): FragmentBindingInflater<FragmentHomeBinding> =
        FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherFlow.onEach {
            when (it) {
                is HomeViewModel.HomeEvent.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.messageText ?: getText(it.message ?: R.string.service_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is HomeViewModel.HomeEvent.IsLoading -> {

                }

                is HomeViewModel.HomeEvent.Success -> {
                    initScreen(it.weatherResponse)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initScreen(weatherResponse: WeatherResponse?) {
        weatherResponse?.let {
            with(binding) {
                tvDegree.text = buildString {
                    append(weatherResponse.currentWeather?.temperature.toString())
                    append(" ")
                    append(weatherResponse.dailyUnits?.temperature2mMax)
                }

                tvLocation.text = weatherResponse.timezone

                Util.getWeatherType(
                    weatherResponse.currentWeather?.weathercode, requireContext()
                )?.let {
                    tvWeatherType.text = it.weather
                    ivWeather.setImageResource(it.weatherImage)
                    root.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            it.backgroundColor
                        )
                    )
                }

                weatherResponse.daily?.let { daily ->
                    homeAdapter = HomeAdapter(daily,it.dailyUnits?.temperature2mMax)
                    rvDaily.adapter = homeAdapter
                }
            }
        }
    }
}