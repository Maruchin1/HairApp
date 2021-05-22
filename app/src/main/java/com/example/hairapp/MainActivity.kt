package com.example.hairapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.common.navigation.AppNavigator
import com.example.corev2.navigation.HomeDestination
import com.example.hairapp.framework.bindActivity
import com.example.hairapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.android.inject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var homeDestination: HomeDestination

    private val appNavigator: AppNavigator by inject()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindActivity(R.layout.activity_main, viewModel = null)

//        appNavigator.toDestination(this, HomeDestination())
        homeDestination.navigate(this, Unit)
    }

}