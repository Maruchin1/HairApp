package com.example.hairapp.page_product_form

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityProductFormBinding
import com.example.hairapp.framework.*
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_product_form.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFormActivity : AppCompatActivity() {

    private val viewModel: ProductFormViewModel by viewModel()

    fun takePhoto() {
        ImagePicker.with(this)
            .crop(x = 1f, y = 1f)
            .compress(maxSize = 1024)
            .maxResultSize(width = 1080, height = 1080)
            .start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityProductFormBinding>(R.layout.activity_product_form, viewModel)
        setSystemColors(R.color.color_primary)

        val editProductId = intent.getIntExtra(IN_EDIT_PRODUCT_ID, -1)
        if (editProductId != -1)
            setEditProduct(editProductId)

        toolbar.onMenuOptionClick = { saveProduct() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val photo = data?.data
            viewModel.productPhoto.value = photo
        }
    }

    private fun setEditProduct(productId: Int) = lifecycleScope.launch {
        toolbar.title = "Edytuj produkt"
        viewModel.setEditProductAsync(productId)
            .await()
            .onFailure { showErrorSnackbar(it.message) }
    }

    private fun saveProduct() = lifecycleScope.launch {
        viewModel.saveProduct()
            .onSuccess { finish() }
            .onFailure { showErrorSnackbar(it.message) }
    }

    companion object {
        private const val IN_EDIT_PRODUCT_ID = "in-edit-product-id"

        fun makeIntent(context: Context, editProductId: Int?): Intent {
            return Intent(context, ProductFormActivity::class.java)
                .putExtra(IN_EDIT_PRODUCT_ID, editProductId)
        }
    }
}