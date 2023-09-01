package com.btcturk.detail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.btcturk.base.types.FragmentBindingInflater
import com.btcturk.base.ui.BaseFragment
import com.btcturk.detail.databinding.FragmentDetailBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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

        binding.toolbar.title = detailArgs.pair
        binding.toolbar.setTitleTextColor(requireContext().getColor(com.btcturk.base.R.color.white))

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
                    setLineChartData(it.klineResponse?.c)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setLineChartData(close: ArrayList<Double>?) {
        binding.lineChart.apply {
            legend.isEnabled = false
            description.isEnabled = false
            isHighlightPerTapEnabled = false
            isHighlightPerDragEnabled = true

            xAxis.setDrawGridLines(false)
            xAxis.setDrawLabels(false)

            axisRight.setDrawAxisLine(false)
            axisRight.setDrawGridLines(false)

            axisRight.setLabelCount(5, true)

            axisRight.textColor = Color.WHITE

            axisLeft.isEnabled = false
        }

        val entryArrayList: ArrayList<Entry> = ArrayList()

        close?.forEachIndexed { index, _ ->
            entryArrayList.add(
                Entry(
                    index.toFloat(),
                    close[index].toFloat()
                )
            )
        }
        val lineDataSet = LineDataSet(entryArrayList, "")

        lineDataSet.lineWidth = 2f
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)

        binding.lineChart.data = LineData(lineDataSet)
        binding.lineChart.invalidate()
    }
}