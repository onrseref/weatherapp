package com.weather.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.weather.base.types.FragmentBindingInflater

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    abstract fun inflate(): FragmentBindingInflater<VB>
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding?.let {
            return it.root
        }

        val binding = inflate().invoke(inflater, container, false)
        _binding = binding

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}