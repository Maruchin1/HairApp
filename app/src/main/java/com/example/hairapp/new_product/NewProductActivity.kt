package com.example.hairapp.new_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.hairapp.R
import com.example.hairapp.bind
import com.example.hairapp.databinding.ActivityNewProductBinding
import com.example.hairapp.setNavigationColor
import com.example.hairapp.setStatusBarColor

class NewProductActivity : AppCompatActivity() {

    private val viewModel: NewProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityNewProductBinding>(R.layout.activity_new_product, viewModel)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_gray_background)
    }
}