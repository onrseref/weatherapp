package com.btcturk.listing

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.btcturk.base.types.FragmentBindingInflater
import com.btcturk.base.ui.BaseFragment
import com.btcturk.data.dto.ticker.TickerData
import com.btcturk.data.local.PairEntity
import com.btcturk.listing.adapter.FavoritesAdapter
import com.btcturk.listing.adapter.ListingAdapter
import com.btcturk.listing.databinding.FragmentListingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListingFragment : BaseFragment<FragmentListingBinding>() {

    override fun inflate(): FragmentBindingInflater<FragmentListingBinding> =
        FragmentListingBinding::inflate

    private var tickerList: ArrayList<TickerData>? = null

    private var listingAdapter: ListingAdapter? = null
    private var favoritesAdapter: FavoritesAdapter? = null

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
                    tickerList = it.tickerResponse?.data
                    setupTickerAdapter(tickerList)
                }
                is ListingViewModel.TickerEvent.FavoriteList -> {
                    setupFavoritesAdapter(it.favoriteList as ArrayList<PairEntity>)
                }
                is ListingViewModel.TickerEvent.HasAdded -> {
                    if (it.hasAdded) {
                        Toast.makeText(
                            requireContext(),
                            R.string.added_to_favorites,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            R.string.removed_from_favorites,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    tickerList?.indexOf(it.tickerData)
                        ?.let { index -> listingAdapter?.notifyItemChanged(index) }

                    viewModel.fetchFavoriteList()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setupTickerAdapter(data: ArrayList<TickerData>?) {
        data?.let {
            listingAdapter = ListingAdapter(it, { tickerData ->
                val action =
                    ListingFragmentDirections.listToDetail(
                        tickerData?.pair,
                        (tickerData?.timestamp?.div(1000)).toString()
                    )
                findNavController().navigate(action)
            }, { ticker ->
                ticker?.let {
                    viewModel.checkExistFavoritesAndAction(ticker)
                }
            })
            binding.rvTicker.adapter = listingAdapter
        }
    }

    private fun setupFavoritesAdapter(favoriteList: ArrayList<PairEntity>) {
        if (favoriteList.size > 0) {
            binding.tvFavorites.visibility = VISIBLE
            binding.rvFavorites.visibility = VISIBLE

            favoritesAdapter = FavoritesAdapter(favoriteList) { tickerData ->
                val action =
                    ListingFragmentDirections.listToDetail(
                        tickerData?.pair,
                        (tickerData?.timestamp?.div(1000)).toString()
                    )
                findNavController().navigate(action)
            }
            binding.rvFavorites.adapter = favoritesAdapter
        } else {
            binding.tvFavorites.visibility = GONE
            binding.rvFavorites.visibility = GONE
        }
    }
}