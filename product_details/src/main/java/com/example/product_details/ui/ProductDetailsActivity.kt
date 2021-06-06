package com.example.product_details.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.SystemColors
import com.example.navigation.ProductDetailsParams
import com.example.navigation.destinationParams
import com.example.product_details.R
import com.example.product_details.databinding.ActivityProductDetailsBinding
import com.example.product_details.model.ProductDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>(
    bindingInflater = ActivityProductDetailsBinding::inflate
) {

    private val params by destinationParams<ProductDetailsParams>()
    private val viewModel by viewModel<ProductDetailsViewModel>()
    private val useCaseActions by inject<ActionsHandler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onProductSelected(params.productId)
        useCaseActions.bind(this)
        setupToolbar()
        observeState()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.allLight()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.product_photo -> viewModel.onCapturePhoto()
                R.id.remove_product_photo -> viewModel.onRemoveProductPhoto()
                R.id.delete_product -> viewModel.onDeleteProduct()
            }
            true
        }
    }

    private fun observeState() = lifecycleScope.launchWhenStarted {
        viewModel.state.collectLatest {
            finishWhenDeleted(it.productDeleted)
        }
    }

    private fun finishWhenDeleted(productDeleted: Boolean) {
        if (productDeleted) finish()
    }
}