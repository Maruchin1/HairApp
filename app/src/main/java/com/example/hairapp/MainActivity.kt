package com.example.hairapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.databinding.ActivityMainBinding
import com.example.navigation.DestinationType
import com.example.navigation.HomeDestination
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {

    private val homeDestination: HomeDestination by inject(named(DestinationType.HOME))
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            homeDestination.navigate(this@MainActivity)
        }
    }

}