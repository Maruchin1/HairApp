package com.example.product_details.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.SystemColors
import com.example.corev2.ui.setVisibleOrGone
import com.example.navigation.ProductDetailsParams
import com.example.navigation.destinationParams
import com.example.product_details.R
import com.example.product_details.databinding.ActivityProductDetailsBinding
import com.example.product_details.model.PageMode
import com.example.product_details.model.ProductDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>(
    bindingInflater = ActivityProductDetailsBinding::inflate
) {

    private val params by destinationParams<ProductDetailsParams>()
    private val viewModel by viewModel<ProductDetailsViewModel> { parametersOf(params.productId) }
    private val actionsHandler by inject<ActionsHandler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionsHandler.bind(this)
        viewModel.onProductSelected(params.productId)
        SystemColors(this)
            .allLight()
            .apply()
        setupToolbar()
        setupEditModeToolbar()
        observeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onFinishEdition()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
    }

    private fun setupToolbar() = binding.toolbar.apply {
        setNavigationOnClickListener { finish() }
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit_product -> {
                    animateStartEdition()
                    viewModel.onEditProduct()
                }
                R.id.delete_product -> viewModel.onDeleteProduct()
            }
            true
        }
    }

    private fun setupEditModeToolbar() = binding.editModeToolbar.apply {
        setNavigationOnClickListener {
            animateFinishEdition()
            viewModel.onFinishEdition()
        }
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.capture_product_photo -> viewModel.onCapturePhoto()
                R.id.remove_product_photo -> viewModel.onRemoveProductPhoto()
            }
            true
        }
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.pageMode.collectLatest {
                updateToolbarMode(it)
                updateStatusBarColor(it)
            }
        }
    }

    private fun updateToolbarMode(pageMode: PageMode) = binding.apply {
        toolbar.setVisibleOrGone(pageMode == PageMode.DISPLAY)
        editModeToolbar.setVisibleOrGone(pageMode == PageMode.EDIT)
    }

    private fun updateStatusBarColor(pageMode: PageMode) = binding.apply {
        when (pageMode) {
            PageMode.DISPLAY -> animateStatusBarToDisplayColor()
            PageMode.EDIT -> animateStatusBarToEditColor()
        }
    }
}