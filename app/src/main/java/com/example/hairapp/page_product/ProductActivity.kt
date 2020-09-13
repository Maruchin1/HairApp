package com.example.hairapp.page_product

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hairapp.R
import com.example.hairapp.databinding.ActivityProductBinding
import com.example.hairapp.framework.*
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.coroutines.launch
import kotlin.math.abs


@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private val viewModel: ProductViewModel by viewModels()

    fun deleteProduct() = withConfirmDialog(
        getString(R.string.confirm_delete),
        getString(R.string.product_activity_confirm_delete_message)
    ) {
        lifecycleScope.launch {
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
        val productName = intent.getStringExtra(EXTRA_PRODUCT_NAME)!!
        viewModel.selectProduct(productName)
    }

    private fun setupCollapsingToolbar() {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            toolbar_title.alpha = abs(verticalOffset / appbar.totalScrollRange.toFloat())
        })
    }

    companion object {
        const val EXTRA_PRODUCT_NAME = "extra-product-name"
    }
}