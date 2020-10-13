package com.example.hairapp.page_product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityProductBinding
import com.example.hairapp.framework.*
import com.example.hairapp.page_product_form.ProductFormActivity
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class ProductActivity : AppCompatActivity() {

    private val viewModel: ProductViewModel by viewModel()

    fun editProduct() {
        viewModel.getProductId()?.let { productId ->
            val intent = ProductFormActivity.makeIntent(this, productId)
            startActivity(intent)
        }
    }

    fun deleteProduct() = lifecycleScope.launch {
        val confirmed = confirmDialog(
            getString(R.string.confirm_delete),
            getString(R.string.product_activity_confirm_delete_message)
        )
        if (confirmed) {
            viewModel.deleteProduct()
                .onSuccess { finish() }
                .onFailure { showErrorSnackbar(it.message) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind<ActivityProductBinding>(R.layout.activity_product, viewModel)
        setStatusBarColor(R.color.color_transparent)
        setNavigationColor(R.color.color_background)

        setupCollapsingToolbar()
        initViewModel()
    }

    private fun initViewModel() {
        val productId = intent.getIntExtra(IN_PRODUCT_ID, -1)
        if (productId != -1)
            viewModel.selectProduct(productId)
    }

    private fun setupCollapsingToolbar() {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            toolbar_title.alpha = abs(verticalOffset / appbar.totalScrollRange.toFloat())
        })
    }

    companion object {
        private const val IN_PRODUCT_ID = "in-product-id"

        fun makeIntent(context: Context, productId: Int): Intent {
            return Intent(context, ProductActivity::class.java)
                .putExtra(IN_PRODUCT_ID, productId)
        }
    }
}