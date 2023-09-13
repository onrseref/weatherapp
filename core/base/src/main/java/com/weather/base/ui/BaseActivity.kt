package com.weather.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.weather.base.types.ActivityBindingInflater

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB
    abstract fun inflate(): ActivityBindingInflater<VB>
    abstract fun bindView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate().invoke(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

}