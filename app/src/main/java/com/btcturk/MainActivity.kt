package com.btcturk

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.btcturk.base.types.ActivityBindingInflater
import com.btcturk.base.ui.BaseActivity
import com.btcturk.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inflate(): ActivityBindingInflater<ActivityMainBinding> =
        ActivityMainBinding::inflate

    override fun bindView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.mainTb.setupWithNavController(navController, null)
    }
}