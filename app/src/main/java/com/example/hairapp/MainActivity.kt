package com.example.hairapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hairapp.framework.bindActivity
import com.example.hairapp.databinding.ActivityMainBinding
import com.example.hairapp.framework.SystemColors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindActivity(R.layout.activity_main, viewModel = null)
        SystemColors(this)
            .lightStatusBar()
            .darkNavigationBar()
            .apply()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHostFragment.navController)
    }

}