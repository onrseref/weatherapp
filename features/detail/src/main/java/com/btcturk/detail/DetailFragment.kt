package com.btcturk.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.btcturk.base.types.FragmentBindingInflater
import com.btcturk.base.ui.BaseFragment
import com.btcturk.detail.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val detailViewModel: DetailViewModel by viewModels()
    private val detailArgs: DetailFragmentArgs by navArgs()

    override fun inflate(): FragmentBindingInflater<FragmentDetailBinding> =
        FragmentDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.fetchKline(detailArgs.pair, detailArgs.to)

        detailViewModel.eventsFlow.onEach {
            when (it) {
                is DetailViewModel.DetailEvent.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.messageText ?: getText(
                            it.message ?: R.string.service_error
                        ),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is DetailViewModel.DetailEvent.IsLoading -> {
                }
                is DetailViewModel.DetailEvent.SuccessDetail -> {

                }
            }
        }.launchIn(lifecycleScope)
    }
}