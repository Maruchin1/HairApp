package com.example.hairapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        BottomNavMediator(binding.bottomNav, supportFragmentManager)
    }

}