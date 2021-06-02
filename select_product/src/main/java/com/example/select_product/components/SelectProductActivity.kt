package com.example.select_product.components

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.corev2.entities.Product
import com.example.corev2.navigation.DestinationWithResult
import com.example.corev2.navigation.SelectProductDestination
import com.example.corev2.ui.BaseActivity
import com.example.corev2.ui.BaseRecyclerAdapter
import com.example.corev2.ui.SystemColors
import com.example.select_product.R
import com.example.select_product.databinding.ActivitySelectProductBinding
import com.example.select_product.databinding.ItemProductBinding
import org.koin.android.ext.android.inject

internal class SelectProductActivity : BaseActivity<ActivitySelectProductBinding>(
    bindingInflater = ActivitySelectProductBinding::inflate
) {

    private val params: SelectProductDestination.Params?
        get() = intent.getParcelableExtra(DestinationWithResult.EXTRA_PARAMS)

    private val viewModel by inject<SelectProductViewModel>()
    private val productsAdapter by lazy { ProductsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupToolbar()
        setupProductsRecycler()
    }

    override fun setupSystemColors(systemColors: SystemColors) {
        systemColors.apply {
            allLight()
        }
    }

    private fun setupViewModel() {
        params?.let {
            viewModel.onProductTypeSelected(it.productType)
        }
    }

    private fun setupToolbar() {
        viewModel.selectedProductType.observe(this) {
            binding.toolbar.setTitle(it?.resId ?: R.string.products)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupProductsRecycler() {
        binding.productsRecycler.adapter = productsAdapter
        productsAdapter.apply {
            addSource(viewModel.products, this@SelectProductActivity)
            setItemComparator { oldItem, newItem -> oldItem.id == newItem.id }
        }
    }

    private fun onProductSelected(product: Product) {
        val resultData = Intent()
            .putExtra(SelectProductDestination.SELECTED_PRODUCT_ID, product.id)
        setResult(Activity.RESULT_OK, resultData)
        finish()
    }

    private inner class ProductsAdapter : BaseRecyclerAdapter<Product, ItemProductBinding>(
        bindingInflater = ItemProductBinding::inflate
    ) {

        override fun onSetItemData(binding: ItemProductBinding, item: Product) {
            binding.apply {
                productName.text = item.name
                manufacturer.text = item.manufacturer
                card.setOnClickListener { onProductSelected(item) }
            }
        }
    }
}