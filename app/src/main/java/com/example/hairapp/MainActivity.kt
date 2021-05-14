package com.example.hairapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.common.navigation.AppNavigator
import com.example.common.navigation.HomeDestination
import com.example.hairapp.framework.bindActivity
import com.example.hairapp.databinding.ActivityMainBinding
import com.example.hairapp.framework.SystemColors
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val appNavigator: AppNavigator by inject()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindActivity(R.layout.activity_main, viewModel = null)
        SystemColors(this)
            .lightStatusBar()
            .darkNavigationBar()
            .apply()

        appNavigator.toDestination(this, HomeDestination())
    }

}