package com.example.hairapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.corev2.navigation.HomeDestination
import com.example.hairapp.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val homeDestination: HomeDestination by inject()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeDestination.navigate(this)
    }

}