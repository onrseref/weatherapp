package com.btcturk.listing

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.btcturk.base.types.FragmentBindingInflater
import com.btcturk.base.ui.BaseFragment
import com.btcturk.data.dto.ticker.TickerData
import com.btcturk.listing.adapter.ListingAdapter
import com.btcturk.listing.databinding.FragmentListingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListingFragment : BaseFragment<FragmentListingBinding>() {

    override fun inflate(): FragmentBindingInflater<FragmentListingBinding> =
        FragmentListingBinding::inflate

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
                    setupTickerAdapter(it.tickerResponse?.data)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setupTickerAdapter(data: ArrayList<TickerData>?) {
        data?.let {
            val listingAdapter = ListingAdapter(it) { tickerData ->
                val action =
                    ListingFragmentDirections.listToDetail(
                        tickerData?.pair,
                        (tickerData?.timestamp?.div(1000)).toString()
                    )
                findNavController().navigate(action)
            }
            binding.rvTicker.adapter = listingAdapter
        }
    }
}