package com.example.calefit.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.calefit.R
import com.example.calefit.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    override fun onStart() {
        super.onStart()
        startBottomNavigation()
    }

    private fun startBottomNavigation() {
        val navController = findNavController(R.id.fv_home_fragments)

        binding.navBottom.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.plannerFragment -> hideBottomNav()
                R.id.exerciseSelectFragment -> hideBottomNav()
                R.id.exerciseDetailFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.navBottom.isVisible = true
    }

    private fun hideBottomNav() {
        binding.navBottom.isVisible = false
    }
}