package com.example.hairapp.presentation.new_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.bind
import com.example.hairapp.databinding.ActivityNewProductBinding
import com.example.hairapp.setNavigationColor
import com.example.hairapp.setStatusBarColor
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_new_product.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewProductActivity : AppCompatActivity() {

    private val viewModel: NewProductViewModel by viewModels()

    fun saveProduct() {
        lifecycleScope.launch {
            val error = viewModel.saveProduct()
            if (error == null) finish() else showError(error)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityNewProductBinding>(R.layout.activity_new_product, viewModel)
        setStatusBarColor(R.color.color_primary)
        setNavigationColor(R.color.color_gray_background)
    }

    private fun showError(message: String) {
        Snackbar.make(coordinator, message, Snackbar.LENGTH_SHORT).show()
    }

}