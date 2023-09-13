package com.weather.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.weather.base.types.FragmentBindingInflater
import com.weather.base.ui.BaseFragment
import com.weather.data.dto.Daily
import com.weather.home.adapter.HomeAdapter
import com.weather.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun inflate(): FragmentBindingInflater<FragmentHomeBinding> =
        FragmentHomeBinding::inflate

    private var homeAdapter: HomeAdapter? = null

    private val viewModel: HomeViewModel by viewModels()

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
                    Toast.makeText(
                        requireContext(),
                        it.weatherResponse?.timezone,
                        Toast.LENGTH_LONG
                    ).show()
                }
                null -> {
                    Toast.makeText(
                        requireContext(),
                        getText(R.string.service_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setupHomeAdapter(data: ArrayList<Int>?) {
        data?.let {
            homeAdapter = HomeAdapter(it) {

            }
            binding.rvHome.adapter = homeAdapter
        }
    }
}