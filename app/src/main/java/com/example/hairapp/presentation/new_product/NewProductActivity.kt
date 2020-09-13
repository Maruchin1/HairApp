package com.example.hairapp.presentation.new_product

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.bind
import com.example.hairapp.databinding.ActivityNewProductBinding
import com.example.hairapp.setNavigationColor
import com.example.hairapp.setStatusBarColor
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_new_product.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewProductActivity : AppCompatActivity() {

    private val viewModel: NewProductViewModel by viewModels()

    fun takePhoto() {
        ImagePicker.with(this)
            .crop(x = 4f, y = 3f)
            .compress(maxSize = 1024)
            .maxResultSize(width = 1080, height = 810)
            .start()
    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val photo = data?.data
            viewModel.productPhoto.value = photo
        }
    }

    private fun showError(message: String) {
        Snackbar.make(coordinator, message, Snackbar.LENGTH_SHORT).show()
    }

}