package com.weather.listing

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.weather.base.types.FragmentBindingInflater
import com.weather.base.ui.BaseFragment
import com.weather.data.dto.WeatherData
import com.weather.listing.adapter.ListingAdapter
import com.weather.listing.databinding.FragmentListingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListingFragment : BaseFragment<FragmentListingBinding>() {

    override fun inflate(): FragmentBindingInflater<FragmentListingBinding> =
        FragmentListingBinding::inflate

    private var tickerList: ArrayList<WeatherData>? = null

    private var listingAdapter: ListingAdapter? = null

    private val viewModel: ListingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.eventsFlow.onEach {
            when (it) {
                is ListingViewModel.TickerEvent.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.messageText ?: getText(it.message ?: R.string.service_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ListingViewModel.TickerEvent.IsLoading -> {
                }

                is ListingViewModel.TickerEvent.Success -> {
                    tickerList = it.weatherResponse?.data
                    setupTickerAdapter(tickerList)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setupTickerAdapter(data: ArrayList<WeatherData>?) {
        data?.let {
            listingAdapter = ListingAdapter(it, { tickerData ->
                /*val action =
                    ListingFragmentDirections.listToDetail(
                        tickerData?.pair,
                        (tickerData?.timestamp?.div(1000)).toString()
                    )
                findNavController().navigate(action)*/
            }, {

            })
            binding.rvTicker.adapter = listingAdapter
        }
    }
}