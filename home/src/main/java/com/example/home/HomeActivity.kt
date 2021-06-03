package com.example.home

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.SystemColors
import com.example.home.databinding.ActivityHomeBinding

internal class HomeActivity : BaseActivity<ActivityHomeBinding>(
    bindingInflater = ActivityHomeBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomNav()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            lightStatusBar()
            darkNavigationBar()
        }
    }

    private fun setupBottomNav() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHostFragment.navController)
    }
}